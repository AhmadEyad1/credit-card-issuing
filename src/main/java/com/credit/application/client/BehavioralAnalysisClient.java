package com.credit.application.client;

import com.credit.domain.entity.CreditCardApplicant;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.concurrent.CompletableFuture;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
public interface BehavioralAnalysisClient {

    CompletableFuture<Double> analyze(final CreditCardApplicant applicant, final String bankStatementUrl) throws JsonProcessingException;
}