package com.credit.infrastructure.client;

import com.credit.domain.entity.CreditCardApplicant;
import com.credit.infrastructure.client.employmentVerification.EmploymentVerificationApiClient;
import com.credit.infrastructure.client.employmentVerification.config.ApiConfig;
import com.credit.infrastructure.client.employmentVerification.config.EmploymentVerificationIntegrationConfig;
import com.credit.infrastructure.client.employmentVerification.dto.EmploymentVerificationResponse;
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
public class EmploymentVerificationApiClientTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private EmploymentVerificationIntegrationConfig config;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private EmploymentVerificationApiClient employmentVerificationApiClient;

    @Test
    public void shouldVerifyEmploymentForCreditCardApplicant() throws Exception {
        final CreditCardApplicant applicant = CreditCardApplicant.builder()
                .personalIdentityNumber("123456789")
                .name("John Doe")
                .employer("Test Company")
                .employmentStatus("Employed")
                .annualIncome(100000)
                .build();

        final String apiUrl = "http://api.example.com";
        final ApiConfig apiConfig = new ApiConfig();
        apiConfig.setUrl(apiUrl);

        final EmploymentVerificationResponse verificationResponse = new EmploymentVerificationResponse(true);
        final String responseBody = new ObjectMapper().writeValueAsString(verificationResponse);

        final HttpRequest request = new HttpRequest(HttpMethod.GET, apiUrl, null, null, null);

        when(config.getApi()).thenReturn(apiConfig);
        when(apiClient.prepareHTTPRequest(HttpMethod.GET, apiUrl, null, Map.of(
                "personalIdentityNumber", applicant.getPersonalIdentityNumber(),
                "name", applicant.getName(),
                "employer", applicant.getEmployer(),
                "employmentStatus", applicant.getEmploymentStatus(),
                "annualIncome", String.valueOf(applicant.getAnnualIncome())
        ))).thenReturn(request);

        final ResponseEntity<String> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);
        when(apiClient.doHTTPRequest(request)).thenReturn(responseEntity);

        when(objectMapper.readValue(responseBody, EmploymentVerificationResponse.class)).thenReturn(verificationResponse);

        final boolean isVerified = employmentVerificationApiClient.isVerified(applicant);

        assertTrue(isVerified, "Applicant should be verified");

        verify(apiClient).prepareHTTPRequest(HttpMethod.GET, apiUrl, null, Map.of(
                "personalIdentityNumber", applicant.getPersonalIdentityNumber(),
                "name", applicant.getName(),
                "employer", applicant.getEmployer(),
                "employmentStatus", applicant.getEmploymentStatus(),
                "annualIncome", String.valueOf(applicant.getAnnualIncome())
        ));
        verify(apiClient).doHTTPRequest(request);
        verify(objectMapper).readValue(responseBody, EmploymentVerificationResponse.class);
    }
}