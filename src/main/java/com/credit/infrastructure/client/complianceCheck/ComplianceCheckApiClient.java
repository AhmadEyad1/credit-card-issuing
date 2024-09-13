package com.credit.infrastructure.client.complianceCheck;

import com.credit.application.client.ComplianceCheckClient;
import com.credit.domain.entity.CreditCardApplicant;
import com.credit.infrastructure.client.ApiClient;
import com.credit.infrastructure.client.HttpRequest;
import com.credit.infrastructure.client.complianceCheck.config.ComplianceCheckIntegrationConfig;
import com.credit.infrastructure.client.complianceCheck.dto.ComplianceCheckResponse;
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
public class ComplianceCheckApiClient implements ComplianceCheckClient {

    private final ApiClient apiClient;

    private final ComplianceCheckIntegrationConfig config;

    private final ObjectMapper objectMapper;

    @Override
    public boolean isPassed(final CreditCardApplicant applicant) throws JsonProcessingException {
        final HttpRequest request = apiClient.prepareHTTPRequest(HttpMethod.GET, config.getApi().getUrl(), null, getQueryParams(applicant));
        final ResponseEntity<String> response = apiClient.doHTTPRequest(request);

        final ComplianceCheckResponse complianceCheckResponse = objectMapper.readValue(response.getBody(), ComplianceCheckResponse.class);
        return complianceCheckResponse.isPassed();
    }

    private Map<String, String> getQueryParams(final CreditCardApplicant applicant) {
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("personalIdentityNumber", applicant.getPersonalIdentityNumber());
        queryParams.put("name", applicant.getName());
        queryParams.put("mobileNumber", applicant.getMobileNumber());
        queryParams.put("employer", applicant.getEmployer());
        queryParams.put("employmentStatus", applicant.getEmploymentStatus());
        return queryParams;
    }
}
