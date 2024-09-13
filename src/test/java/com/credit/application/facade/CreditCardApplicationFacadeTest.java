package com.credit.application.facade;

import com.credit.application.dto.CreditCardApplicationRequest;
import com.credit.application.dto.CreditCardApplicationResponse;
import com.credit.application.processor.CreditCardVerificationProcessor;
import com.credit.application.service.FileUploader;
import com.credit.domain.entity.CreditCardVerification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@SpringBootTest
@ActiveProfiles("test")
public class CreditCardApplicationFacadeTest {

    @Autowired
    private CreditCardApplicationFacade facade;

    @MockBean
    private FileUploader fileUploader;

    @MockBean
    private CreditCardVerificationProcessor processor;

    @Test
    public void shouldApplyForCreditCardSuccessfully() throws IOException {
        final CreditCardApplicationRequest request = CreditCardApplicationRequest.builder()
                .emiratesIdNumber("123456789")
                .name("John Doe")
                .mobileNumber("+971501234567")
                .nationality("UAE")
                .address("123 Example Street")
                .income(100000.00)
                .employer("Example Corp")
                .employmentStatus("Employed")
                .requestedCreditLimit(5000.00)
                .bankStatement(null)
                .build();

        final String fileUrl = "https://example.com/bank-statement";

        final CreditCardVerification verification = CreditCardVerification.builder()
                .identityVerified(true)
                .complianceCheckPassed(true)
                .employmentVerified(true)
                .riskEvaluationScore(90)
                .behavioralAnalysisScore(100)
                .build();

        when(fileUploader.uploadFile(any())).thenReturn(fileUrl);
        when(processor.process(any(), any())).thenReturn(verification);

        final CreditCardApplicationResponse response = facade.apply(request);

        assertEquals("Stp", response.getStatus(), "Application status mismatch");
        assertEquals("Congratulations! Your credit card has been issued automatically. You should receive it soon.", response.getMessage(), "Response message mismatch");

        verify(fileUploader).uploadFile(request.getBankStatement());
        verify(processor).process(any(), any());
    }
}