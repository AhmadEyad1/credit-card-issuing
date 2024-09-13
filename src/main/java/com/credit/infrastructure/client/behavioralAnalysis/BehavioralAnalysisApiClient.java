package com.credit.infrastructure.client.behavioralAnalysis;

import com.credit.application.client.BehavioralAnalysisClient;
import com.credit.domain.entity.CreditCardApplicant;
import com.credit.infrastructure.client.ApiClient;
import com.credit.infrastructure.client.HttpRequest;
import com.credit.infrastructure.client.behavioralAnalysis.config.BehavioralAnalysisIntegrationConfig;
import com.credit.infrastructure.client.behavioralAnalysis.dto.BehavioralAnalysisResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@RequiredArgsConstructor
public class BehavioralAnalysisApiClient implements BehavioralAnalysisClient {

    private final ApiClient apiClient;

    private final BehavioralAnalysisIntegrationConfig config;

    private final ObjectMapper objectMapper;

    @Override
    public CompletableFuture<Double> analyze(final CreditCardApplicant applicant, final String bankStatementUrl) throws JsonProcessingException {
        final HttpRequest request = apiClient.prepareHTTPRequest(HttpMethod.GET, config.getApi().getUrl(), null, getQueryParams(applicant, bankStatementUrl));
        final CompletableFuture<ResponseEntity<String>> futureResponse = apiClient.doHTTPRequestAsync(request);

        return futureResponse.thenApply(response -> {
            try {
                final BehavioralAnalysisResponse behavioralAnalysisResponse = objectMapper.readValue(response.getBody(), BehavioralAnalysisResponse.class);
                return behavioralAnalysisResponse.getScore();
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Map<String, String> getQueryParams(final CreditCardApplicant applicant, final String bankStatementUrl) {
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("personalIdentityNumber", applicant.getPersonalIdentityNumber());
        queryParams.put("name", applicant.getName());
        queryParams.put("bankStatementUrl", bankStatementUrl);

        return queryParams;
    }
}