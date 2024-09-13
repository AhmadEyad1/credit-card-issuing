package com.credit.infrastructure.client;

import com.credit.domain.entity.CreditCardApplicant;
import com.credit.infrastructure.client.complianceCheck.ComplianceCheckApiClient;
import com.credit.infrastructure.client.complianceCheck.config.ApiConfig;
import com.credit.infrastructure.client.complianceCheck.config.ComplianceCheckIntegrationConfig;
import com.credit.infrastructure.client.complianceCheck.dto.ComplianceCheckResponse;
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
public class ComplianceCheckApiClientTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private ComplianceCheckIntegrationConfig config;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ComplianceCheckApiClient complianceCheckApiClient;

    @Test
    public void shouldReturnTrueIfComplianceCheckPassed() throws Exception {
        final CreditCardApplicant applicant = CreditCardApplicant.builder()
                .personalIdentityNumber("123Q2")
                .name("Test Name")
                .mobileNumber("+000123456789")
                .employer("Testing LTC")
                .employmentStatus("Employed")
                .build();

        final String apiUrl = "http://api.example.com";
        final ApiConfig apiConfig = new ApiConfig();
        apiConfig.setUrl(apiUrl);
        final ComplianceCheckResponse complianceCheckResponse = new ComplianceCheckResponse(true);
        final String responseBody = new ObjectMapper().writeValueAsString(complianceCheckResponse);

        final HttpRequest request = new HttpRequest(HttpMethod.GET, apiUrl, null, null, null);

        when(config.getApi()).thenReturn(apiConfig);
        when(apiClient.prepareHTTPRequest(HttpMethod.GET, apiUrl, null, Map.of(
                "personalIdentityNumber", applicant.getPersonalIdentityNumber(),
                "name", applicant.getName(),
                "mobileNumber", applicant.getMobileNumber(),
                "employer", applicant.getEmployer(),
                "employmentStatus", applicant.getEmploymentStatus()
        ))).thenReturn(request);

        final ResponseEntity<String> response = new ResponseEntity<>(responseBody, HttpStatus.OK);
        when(apiClient.doHTTPRequest(request)).thenReturn(response);

        when(objectMapper.readValue(responseBody, ComplianceCheckResponse.class))
                .thenReturn(complianceCheckResponse);

        final boolean isPassed = complianceCheckApiClient.isPassed(applicant);

        assertTrue(isPassed, "Compliance check should pass");

        verify(apiClient).prepareHTTPRequest(HttpMethod.GET, apiUrl, null, Map.of(
                "personalIdentityNumber", applicant.getPersonalIdentityNumber(),
                "name", applicant.getName(),
                "mobileNumber", applicant.getMobileNumber(),
                "employer", applicant.getEmployer(),
                "employmentStatus", applicant.getEmploymentStatus()
        ));
        verify(apiClient).doHTTPRequest(request);
        verify(objectMapper).readValue(responseBody, ComplianceCheckResponse.class);
    }
}