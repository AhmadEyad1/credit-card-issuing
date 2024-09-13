package com.credit.infrastructure.client;

import com.credit.domain.entity.CreditCardApplicant;
import com.credit.infrastructure.client.identityVerification.IdentityVerificationApiClient;
import com.credit.infrastructure.client.identityVerification.config.ApiConfig;
import com.credit.infrastructure.client.identityVerification.config.IdentityVerificationIntegrationConfig;
import com.credit.infrastructure.client.identityVerification.dto.IdentityVerificationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@SpringBootTest
@ActiveProfiles("test")
public class IdentityVerificationApiClientTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private IdentityVerificationIntegrationConfig config;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private IdentityVerificationApiClient identityVerificationApiClient;

    @Test
    public void shouldVerifyIdentityForCreditCardApplicant() throws Exception {
        final CreditCardApplicant applicant = CreditCardApplicant.builder()
                .personalIdentityNumber("123456789")
                .name("Jane Doe")
                .mobileNumber("+000123456789")
                .nationality("Testland")
                .residentialAddress("123 Test St, Test City")
                .build();

        final String apiUrl = "http://api.example.com";
        final ApiConfig apiConfig = new ApiConfig();
        apiConfig.setUrl(apiUrl);

        final IdentityVerificationResponse verificationResponse = new IdentityVerificationResponse(true);
        final String responseBody = new ObjectMapper().writeValueAsString(verificationResponse);

        final HttpRequest request = new HttpRequest(HttpMethod.GET, apiUrl, null, null, null);

        when(config.getApi()).thenReturn(apiConfig);
        when(apiClient.prepareHTTPRequest(HttpMethod.GET, apiUrl, null, Map.of(
                "personalIdentityNumber", applicant.getPersonalIdentityNumber(),
                "name", applicant.getName(),
                "mobileNumber", applicant.getMobileNumber(),
                "nationality", applicant.getNationality(),
                "residentialAddress", applicant.getResidentialAddress()
        ))).thenReturn(request);

        final ResponseEntity<String> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);
        when(apiClient.doHTTPRequest(request)).thenReturn(responseEntity);

        when(objectMapper.readValue(responseEntity.getBody(), IdentityVerificationResponse.class)).thenReturn(verificationResponse);

        final boolean isVerified = identityVerificationApiClient.isVerified(applicant);

        assertTrue(isVerified, "Applicant's identity should be verified");

        verify(apiClient).prepareHTTPRequest(HttpMethod.GET, apiUrl, null, Map.of(
                "personalIdentityNumber", applicant.getPersonalIdentityNumber(),
                "name", applicant.getName(),
                "mobileNumber", applicant.getMobileNumber(),
                "nationality", applicant.getNationality(),
                "residentialAddress", applicant.getResidentialAddress()
        ));
        verify(apiClient).doHTTPRequest(request);
        verify(objectMapper).readValue(responseEntity.getBody(), IdentityVerificationResponse.class);
    }
}