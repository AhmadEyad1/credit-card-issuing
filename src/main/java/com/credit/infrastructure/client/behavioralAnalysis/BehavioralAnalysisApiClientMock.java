package com.credit.infrastructure.client.behavioralAnalysis;

import com.credit.application.client.BehavioralAnalysisClient;
import com.credit.domain.entity.CreditCardApplicant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Component
@Slf4j
public class BehavioralAnalysisApiClientMock implements BehavioralAnalysisClient {

    @Override
    public CompletableFuture<Double> analyze(final CreditCardApplicant applicant, final String bankStatementUrl) {
        log.info("Calling Behavioral Analysis Client API");
        return CompletableFuture.completedFuture(70.0);
    }
}
