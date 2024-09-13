package com.credit.infrastructure.client.identityVerification;

import com.credit.application.client.IdentityVerificationClient;
import com.credit.domain.entity.CreditCardApplicant;
import com.credit.infrastructure.client.ApiClient;
import com.credit.infrastructure.client.HttpRequest;
import com.credit.infrastructure.client.identityVerification.config.IdentityVerificationIntegrationConfig;
import com.credit.infrastructure.client.identityVerification.dto.IdentityVerificationResponse;
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
public class IdentityVerificationApiClient implements IdentityVerificationClient {

    private final ApiClient apiClient;

    private final IdentityVerificationIntegrationConfig config;

    private final ObjectMapper objectMapper;

    @Override
    public boolean isVerified(final CreditCardApplicant applicant) throws JsonProcessingException {
        final HttpRequest request = apiClient.prepareHTTPRequest(HttpMethod.GET, config.getApi().getUrl(), null, getQueryParams(applicant));
        final ResponseEntity<String> response = apiClient.doHTTPRequest(request);

        final IdentityVerificationResponse identityVerificationResponse = objectMapper.readValue(response.getBody(), IdentityVerificationResponse.class);
        return identityVerificationResponse.isVerified();
    }

    private Map<String, String> getQueryParams(final CreditCardApplicant applicant) {
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("personalIdentityNumber", applicant.getPersonalIdentityNumber());
        queryParams.put("name", applicant.getName());
        queryParams.put("mobileNumber", applicant.getMobileNumber());
        queryParams.put("nationality", applicant.getNationality());
        queryParams.put("residentialAddress", applicant.getResidentialAddress());
        return queryParams;
    }
}
