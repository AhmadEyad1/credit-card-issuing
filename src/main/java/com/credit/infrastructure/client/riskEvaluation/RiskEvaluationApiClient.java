package com.credit.infrastructure.client.riskEvaluation;

import com.credit.application.client.RiskEvaluationClient;
import com.credit.domain.entity.CreditCardApplicant;
import com.credit.infrastructure.client.ApiClient;
import com.credit.infrastructure.client.HttpRequest;
import com.credit.infrastructure.client.riskEvaluation.config.RiskEvaluationIntegrationConfig;
import com.credit.infrastructure.client.riskEvaluation.dto.RiskEvaluationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@RequiredArgsConstructor
public class RiskEvaluationApiClient implements RiskEvaluationClient {

    private final ApiClient apiClient;

    private final RiskEvaluationIntegrationConfig config;

    private final ObjectMapper objectMapper;

    @Override
    public double evaluate(final CreditCardApplicant applicant) throws JsonProcessingException {
        final HttpRequest request = apiClient.prepareHTTPRequest(HttpMethod.GET, config.getApi().getUrl(), null, getQueryParams(applicant));
        final ResponseEntity<String> response = apiClient.doHTTPRequest(request);

        final RiskEvaluationResponse riskEvaluationResponse = objectMapper.readValue(response.getBody(), RiskEvaluationResponse.class);
        return riskEvaluationResponse.getScore();
    }

    private Map<String, String> getQueryParams(final CreditCardApplicant applicant) {
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("personalIdentityNumber", applicant.getPersonalIdentityNumber());
        queryParams.put("name", applicant.getName());
        return queryParams;
    }
}