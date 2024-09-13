package com.credit.infrastructure.client;

import com.credit.application.dto.CreditCardApplicationDto;
import com.credit.infrastructure.client.creditCardProcessing.CreditCardProcessingApiClient;
import com.credit.infrastructure.client.creditCardProcessing.config.ApiConfig;
import com.credit.infrastructure.client.creditCardProcessing.config.CreditCardProcessingIntegrationConfig;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@SpringBootTest
@ActiveProfiles("test")
public class CreditCardProcessingApiClientTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CreditCardProcessingIntegrationConfig config;

    @InjectMocks
    private CreditCardProcessingApiClient creditCardProcessingApiClient;

    @Test
    public void shouldProcessCreditCardApplication() {
        final CreditCardApplicationDto applicationDto = new CreditCardApplicationDto();
        final String apiUrl = "http://api.example.com";
        final HttpRequest request = new HttpRequest(HttpMethod.POST, apiUrl, null, null, null);
        final ApiConfig apiConfig = new ApiConfig();
        apiConfig.setUrl(apiUrl);

        when(config.getApi()).thenReturn(apiConfig);

        when(apiClient.prepareHTTPRequest(HttpMethod.POST, apiUrl, applicationDto, null)).thenReturn(request);

        CompletableFuture<ResponseEntity<String>> futureResponse = CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.OK));
        when(apiClient.doHTTPRequestAsync(request)).thenReturn(futureResponse);

        creditCardProcessingApiClient.process(applicationDto);

        verify(apiClient).prepareHTTPRequest(HttpMethod.POST, apiUrl, applicationDto, null);
        verify(apiClient).doHTTPRequestAsync(request);
    }
}