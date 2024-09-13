package com.credit.infrastructure.client.employmentVerification;

import com.credit.application.client.EmploymentVerificationClient;
import com.credit.domain.entity.CreditCardApplicant;
import com.credit.infrastructure.client.ApiClient;
import com.credit.infrastructure.client.HttpRequest;
import com.credit.infrastructure.client.employmentVerification.config.EmploymentVerificationIntegrationConfig;
import com.credit.infrastructure.client.employmentVerification.dto.EmploymentVerificationResponse;
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
public class EmploymentVerificationApiClient implements EmploymentVerificationClient {

    private final ApiClient apiClient;

    private final EmploymentVerificationIntegrationConfig config;

    private final ObjectMapper objectMapper;

    @Override
    public boolean isVerified(final CreditCardApplicant applicant) throws JsonProcessingException {
        final HttpRequest request = apiClient.prepareHTTPRequest(HttpMethod.GET, config.getApi().getUrl(), null, getQueryParams(applicant));
        final ResponseEntity<String> response = apiClient.doHTTPRequest(request);

        final EmploymentVerificationResponse employmentVerificationResponse = objectMapper.readValue(response.getBody(), EmploymentVerificationResponse.class);
        return employmentVerificationResponse.isVerified();
    }

    private Map<String, String> getQueryParams(final CreditCardApplicant applicant) {
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("personalIdentityNumber", applicant.getPersonalIdentityNumber());
        queryParams.put("name", applicant.getName());
        queryParams.put("employer", applicant.getEmployer());
        queryParams.put("employmentStatus", applicant.getEmploymentStatus());
        queryParams.put("annualIncome", String.valueOf(applicant.getAnnualIncome()));
        return queryParams;
    }
}