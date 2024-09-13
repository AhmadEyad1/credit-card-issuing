package com.credit.application.processor;

import com.credit.application.client.*;
import com.credit.application.service.TaskExecutor;
import com.credit.domain.entity.CreditCardApplicant;
import com.credit.domain.entity.CreditCardApplicationDetails;
import com.credit.domain.entity.CreditCardVerification;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@SpringBootTest
@ActiveProfiles("test")
public class CreditCardVerificationProcessorTest {

    @Mock
    private IdentityVerificationClient identityVerificationClient;

    @Mock
    private EmploymentVerificationClient employmentVerificationClient;

    @Mock
    private ComplianceCheckClient complianceCheckClient;

    @Mock
    private RiskEvaluationClient riskEvaluationClient;

    @Mock
    private BehavioralAnalysisClient behavioralAnalysisClient;

    @Mock
    private TaskExecutor taskExecutor;

    @InjectMocks
    private CreditCardVerificationProcessor creditCardVerificationProcessor;

    @Test
    public void shouldProcessVerificationSuccessfully() throws Exception {
        final CreditCardApplicant creditCardApplicant = CreditCardApplicant.builder()
                .personalIdentityNumber("123Q2")
                .name("Test Name")
                .mobileNumber("+000123456789")
                .nationality("Nationality")
                .residentialAddress("Address")
                .annualIncome(150000)
                .employer("Testing LTC")
                .employmentStatus("Employed")
                .build();

        final CreditCardApplicationDetails applicationDetails = CreditCardApplicationDetails.builder()
                .bankStatementUrl("http://example.com/statement")
                .requestedCreditLimit(5000)
                .build();

        when(identityVerificationClient.isVerified(creditCardApplicant)).thenReturn(true);

        when(taskExecutor.submit(any(Callable.class)))
                .thenAnswer(invocation -> {
                    Callable<?> callable = invocation.getArgument(0);
                    return CompletableFuture.supplyAsync(() -> {
                        try {
                            return callable.call();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                });

        when(employmentVerificationClient.isVerified(creditCardApplicant)).thenReturn(true);
        when(complianceCheckClient.isPassed(creditCardApplicant)).thenReturn(true);
        when(riskEvaluationClient.evaluate(creditCardApplicant)).thenReturn(80.0);
        when(behavioralAnalysisClient.analyze(creditCardApplicant, applicationDetails.getBankStatementUrl()))
                .thenReturn(CompletableFuture.completedFuture(85.0));

        final CreditCardVerification result = creditCardVerificationProcessor.process(creditCardApplicant, applicationDetails);

        assertNotNull(result);
        assertTrue(result.isIdentityVerified());
        assertTrue(result.isEmploymentVerified());
        assertTrue(result.isComplianceCheckPassed());
        assertEquals(80.0, result.getRiskEvaluationScore(), 0.01);
        assertEquals(85.0, result.getBehavioralAnalysisScore(), 0.01);

        verify(identityVerificationClient).isVerified(creditCardApplicant);
        verify(employmentVerificationClient).isVerified(creditCardApplicant);
        verify(complianceCheckClient).isPassed(creditCardApplicant);
        verify(riskEvaluationClient).evaluate(creditCardApplicant);
        verify(behavioralAnalysisClient).analyze(creditCardApplicant, applicationDetails.getBankStatementUrl());
    }
}