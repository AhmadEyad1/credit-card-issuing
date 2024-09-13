package com.credit.infrastructure.client.backOffice;

import com.credit.application.client.BackOfficeClient;
import com.credit.application.dto.CreditCardApplicationDto;
import com.credit.infrastructure.client.ApiClient;
import com.credit.infrastructure.client.HttpRequest;
import com.credit.infrastructure.client.backOffice.config.BackOfficeIntegrationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@RequiredArgsConstructor
public class BackOfficeApiClient implements BackOfficeClient {

    private final ApiClient apiClient;

    private final BackOfficeIntegrationConfig config;

    @Override
    public void handle(final CreditCardApplicationDto applicationDto) {
        final HttpRequest request = apiClient.prepareHTTPRequest(HttpMethod.POST, config.getApi().getUrl(), applicationDto, null);
        apiClient.doHTTPRequestAsync(request);
    }
}