package com.credit.infrastructure.client;

import com.credit.domain.entity.CreditCardApplicant;
import com.credit.infrastructure.client.riskEvaluation.RiskEvaluationApiClient;
import com.credit.infrastructure.client.riskEvaluation.config.ApiConfig;
import com.credit.infrastructure.client.riskEvaluation.config.RiskEvaluationIntegrationConfig;
import com.credit.infrastructure.client.riskEvaluation.dto.RiskEvaluationResponse;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@SpringBootTest
@ActiveProfiles("test")
public class RiskEvaluationApiClientTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private RiskEvaluationIntegrationConfig config;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private RiskEvaluationApiClient riskEvaluationApiClient;

    @Test
    public void shouldEvaluateRiskForCreditCardApplicant() throws Exception {
        final CreditCardApplicant applicant = CreditCardApplicant.builder()
                .personalIdentityNumber("123456789")
                .name("John Doe")
                .build();

        final String apiUrl = "http://api.example.com";
        final ApiConfig apiConfig = new ApiConfig();
        apiConfig.setUrl(apiUrl);

        final RiskEvaluationResponse riskEvaluationResponse = new RiskEvaluationResponse(75.0);
        final String responseBody = new ObjectMapper().writeValueAsString(riskEvaluationResponse);

        final HttpRequest request = new HttpRequest(HttpMethod.GET, apiUrl, null, null, null);

        when(config.getApi()).thenReturn(apiConfig);
        when(apiClient.prepareHTTPRequest(HttpMethod.GET, apiUrl, null, Map.of(
                "personalIdentityNumber", applicant.getPersonalIdentityNumber(),
                "name", applicant.getName()
        ))).thenReturn(request);

        final ResponseEntity<String> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);
        when(apiClient.doHTTPRequest(request)).thenReturn(responseEntity);

        when(objectMapper.readValue(responseEntity.getBody(), RiskEvaluationResponse.class)).thenReturn(riskEvaluationResponse);

        final double score = riskEvaluationApiClient.evaluate(applicant);

        assertEquals(75.0, score, 0.01, "Risk evaluation score should match");

        verify(apiClient).prepareHTTPRequest(HttpMethod.GET, apiUrl, null, Map.of(
                "personalIdentityNumber", applicant.getPersonalIdentityNumber(),
                "name", applicant.getName()
        ));
        verify(apiClient).doHTTPRequest(request);
        verify(objectMapper).readValue(responseEntity.getBody(), RiskEvaluationResponse.class);
    }
}