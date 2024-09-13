package com.credit.application.handler;

import com.credit.application.dto.CreditCardApplicationDto;
import com.credit.application.event.BackOfficeProcessingEvent;
import com.credit.application.event.CreditCardProcessingEvent;
import com.credit.application.mapper.CreditCardApplicationMapper;
import com.credit.domain.entity.CreditCardApplication;
import com.credit.domain.entity.CreditCardVerification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@SpringBootTest
@ActiveProfiles("test")
public class CreditCardApplicationStatusHandlerTest {

    @Autowired
    private CreditCardApplicationStatusHandler handler;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @MockBean
    private CreditCardApplicationMapper creditCardApplicationMapper;

    private CreditCardApplication application;

    @BeforeEach
    void setUp() {
        final CreditCardApplicationDto creditCardApplicationDto = mock(CreditCardApplicationDto.class);
        when(creditCardApplicationMapper.toApplicationDto(application)).thenReturn(creditCardApplicationDto);

        application = CreditCardApplication.builder().build();
    }

    @AfterEach
    void tearDown() {
        reset(eventPublisher);
    }

    @Test
    public void shouldHandleApplicationWithSTPStatus() {
        final CreditCardVerification verification = CreditCardVerification.builder()
                .identityVerified(true)
                .complianceCheckPassed(true)
                .employmentVerified(true)
                .riskEvaluationScore(98)
                .behavioralAnalysisScore(100)
                .build();

        application.setVerification(verification);

        handler.handle(application);

        assertEquals("STP", application.getStatus().getKey(), "Application status mismatch");

        verify(eventPublisher).publishEvent(any(CreditCardProcessingEvent.class));
        verifyNoMoreInteractions(eventPublisher);
    }

    @Test
    public void shouldHandleApplicationWithNearSTPStatus() {
        final CreditCardVerification verification = CreditCardVerification.builder()
                .identityVerified(true)
                .complianceCheckPassed(true)
                .employmentVerified(false)
                .riskEvaluationScore(85)
                .behavioralAnalysisScore(100)
                .build();

        application.setVerification(verification);

        handler.handle(application);

        assertEquals("NEAR_STP", application.getStatus().getKey(), "Application status mismatch");

        verify(eventPublisher).publishEvent(any(CreditCardProcessingEvent.class));
        verify(eventPublisher).publishEvent(any(BackOfficeProcessingEvent.class));
        verifyNoMoreInteractions(eventPublisher);
    }

    @Test
    public void shouldHandleApplicationWithManualReviewStatus() {
        final CreditCardVerification verification = CreditCardVerification.builder()
                .identityVerified(true)
                .complianceCheckPassed(true)
                .employmentVerified(false)
                .riskEvaluationScore(70)
                .behavioralAnalysisScore(80)
                .build();

        application.setVerification(verification);

        handler.handle(application);

        assertEquals("MANUAL_REVIEW", application.getStatus().getKey(), "Application status mismatch");

        verify(eventPublisher).publishEvent(any(BackOfficeProcessingEvent.class));
        verifyNoMoreInteractions(eventPublisher);
    }

    @Test
    public void shouldHandleApplicationWithRejectedStatusWhenLowVerificationScore() {
        final CreditCardVerification verification = CreditCardVerification.builder()
                .identityVerified(true)
                .complianceCheckPassed(false)
                .employmentVerified(true)
                .riskEvaluationScore(30)
                .behavioralAnalysisScore(15)
                .build();

        application.setVerification(verification);

        handler.handle(application);

        assertEquals("REJECTED", application.getStatus().getKey(), "Application status mismatch");

        verifyNoInteractions(eventPublisher);
    }

    @Test
    public void shouldHandleApplicationWithRejectedStatusWhenIdentityNotVerified() {
        final CreditCardVerification verification = CreditCardVerification.builder()
                .identityVerified(false)
                .complianceCheckPassed(true)
                .employmentVerified(true)
                .riskEvaluationScore(100)
                .behavioralAnalysisScore(100)
                .build();

        application.setVerification(verification);

        handler.handle(application);

        assertEquals("REJECTED", application.getStatus().getKey(), "Application status mismatch");

        verifyNoInteractions(eventPublisher);
    }

    @TestConfiguration
    static class MockitoPublisherConfiguration {

        @Bean
        @Primary
        ApplicationEventPublisher publisher() {
            return mock(ApplicationEventPublisher.class);
        }
    }
}