package com.credit.infrastructure.client.creditCardProcessing;

import com.credit.application.client.CreditCardProcessingClient;
import com.credit.application.dto.CreditCardApplicationDto;
import com.credit.infrastructure.client.ApiClient;
import com.credit.infrastructure.client.HttpRequest;
import com.credit.infrastructure.client.creditCardProcessing.config.CreditCardProcessingIntegrationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@RequiredArgsConstructor
public class CreditCardProcessingApiClient implements CreditCardProcessingClient {

    private final ApiClient apiClient;

    private final CreditCardProcessingIntegrationConfig config;

    @Override
    public void process(final CreditCardApplicationDto applicationDto) {
        final HttpRequest request = apiClient.prepareHTTPRequest(HttpMethod.POST, config.getApi().getUrl(), applicationDto, null);
        apiClient.doHTTPRequestAsync(request);
    }
}