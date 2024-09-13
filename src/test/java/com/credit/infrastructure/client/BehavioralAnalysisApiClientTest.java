package com.credit.infrastructure.client;

import com.credit.domain.entity.CreditCardApplicant;
import com.credit.infrastructure.client.backOffice.config.ApiConfig;
import com.credit.infrastructure.client.behavioralAnalysis.BehavioralAnalysisApiClient;
import com.credit.infrastructure.client.behavioralAnalysis.config.BehavioralAnalysisIntegrationConfig;
import com.credit.infrastructure.client.behavioralAnalysis.dto.BehavioralAnalysisResponse;
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
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@SpringBootTest
@ActiveProfiles("test")
public class BehavioralAnalysisApiClientTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private BehavioralAnalysisIntegrationConfig config;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private BehavioralAnalysisApiClient behavioralAnalysisApiClient;

    @Test
    public void shouldAnalyzeCreditCardApplicant() throws Exception {
        final CreditCardApplicant creditCardApplicant = CreditCardApplicant.builder()
                .personalIdentityNumber("123Q2")
                .name("Test Name")
                .mobileNumber("+000123456789")
                .nationality("Nationality")
                .residentialAddress("Address")
                .annualIncome(150000)
                .employer("Testing LTC")
                .employmentStatus("Employed")
                .build();

        final String bankStatementUrl = "http://example.com/statement";
        final String apiUrl = "http://api.example.com";
        final ApiConfig apiConfig = new ApiConfig();
        apiConfig.setUrl(apiUrl);
        final BehavioralAnalysisResponse analysisResponse = new BehavioralAnalysisResponse(85.5);
        final String responseBody = new ObjectMapper().writeValueAsString(analysisResponse);

        final HttpRequest request = new HttpRequest(HttpMethod.GET, apiUrl, null, null, null);

        when(config.getApi()).thenReturn(apiConfig);
        when(apiClient.prepareHTTPRequest(HttpMethod.GET, apiUrl, null, Map.of(
                "personalIdentityNumber", creditCardApplicant.getPersonalIdentityNumber(),
                "name", creditCardApplicant.getName(),
                "bankStatementUrl", bankStatementUrl
        ))).thenReturn(request);

        final CompletableFuture<ResponseEntity<String>> futureResponse = CompletableFuture.completedFuture(new ResponseEntity<>(responseBody, HttpStatus.OK));
        when(apiClient.doHTTPRequestAsync(request)).thenReturn(futureResponse);

        when(objectMapper.readValue(responseBody, BehavioralAnalysisResponse.class)).thenReturn(analysisResponse);

        final CompletableFuture<Double> scoreFuture = behavioralAnalysisApiClient.analyze(creditCardApplicant, bankStatementUrl);

        final Double score = scoreFuture.get();
        assertEquals(85.5, score, 0.01, "Behavioral analysis score should match");

        verify(apiClient).prepareHTTPRequest(HttpMethod.GET, apiUrl, null, Map.of(
                "personalIdentityNumber", creditCardApplicant.getPersonalIdentityNumber(),
                "name", creditCardApplicant.getName(),
                "bankStatementUrl", bankStatementUrl
        ));
        verify(apiClient).doHTTPRequestAsync(request);
        verify(objectMapper).readValue(responseBody, BehavioralAnalysisResponse.class);
    }
}