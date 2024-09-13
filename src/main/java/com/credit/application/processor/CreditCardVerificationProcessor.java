package com.credit.application.processor;

import com.credit.application.client.*;
import com.credit.application.service.TaskExecutor;
import com.credit.domain.entity.CreditCardApplicant;
import com.credit.domain.entity.CreditCardApplicationDetails;
import com.credit.domain.entity.CreditCardVerification;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Service
@RequiredArgsConstructor
public class CreditCardVerificationProcessor {

    private final IdentityVerificationClient identityVerificationClient;
    private final EmploymentVerificationClient employmentVerificationClient;
    private final ComplianceCheckClient complianceCheckClient;
    private final RiskEvaluationClient riskEvaluationClient;
    private final BehavioralAnalysisClient behavioralAnalysisClient;
    private final TaskExecutor taskExecutor;

    public CreditCardVerification process(final CreditCardApplicant applicant, final CreditCardApplicationDetails applicationDetails) throws JsonProcessingException {

        if (!identityVerificationClient.isVerified(applicant)) {
            return CreditCardVerification.builder()
                    .identityVerified(false)
                    .build();
        }

        try {
            // Use a virtual thread executor for concurrent tasks
            final CompletableFuture<Boolean> employmentVerificationFuture = taskExecutor.submit(() -> employmentVerificationClient.isVerified(applicant));
            final CompletableFuture<Boolean> complianceCheckFuture = taskExecutor.submit(() -> complianceCheckClient.isPassed(applicant));
            final CompletableFuture<Double> riskEvaluationFuture = taskExecutor.submit(() -> riskEvaluationClient.evaluate(applicant));
            final CompletableFuture<Double> behavioralAnalysisFuture = taskExecutor.submit(() -> behavioralAnalysisClient.analyze(applicant, applicationDetails.getBankStatementUrl()).get());

            return CreditCardVerification.builder()
                    .identityVerified(true)
                    .employmentVerified(employmentVerificationFuture.join())
                    .complianceCheckPassed(complianceCheckFuture.join())
                    .riskEvaluationScore(riskEvaluationFuture.join())
                    .behavioralAnalysisScore(behavioralAnalysisFuture.join())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Error processing credit card verification", e);
        }
    }
}