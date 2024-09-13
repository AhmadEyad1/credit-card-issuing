package com.credit.infrastructure.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Component
@RequiredArgsConstructor
public class ApiClient {

    private final RestTemplate restTemplate;

    public HttpRequest prepareHTTPRequest(final HttpMethod method, final String url, final Object requestData, final Map<String, String> queryParams) throws ApiClientException {
        try {
            return HttpRequest.builder()
                    .method(method)
                    .url(url)
                    .body(objectToJson(requestData))
                    .headers(buildHeaders())
                    .queryParams(queryParams)
                    .build();
        } catch (Exception e) {
            throw new ApiClientException("Client Prepare Request Exception: " + e.getMessage(), e);
        }
    }

    public ResponseEntity<String> doHTTPRequest(final HttpRequest request) throws ApiClientException {
        try {
            return restTemplate.exchange(request.getUrl(), request.getMethod(), request.getHttpEntity(), String.class);
        } catch (HttpClientErrorException e) {
            throw new ApiClientException("Client Send Request Exception: " + e.getMessage(), e);
        }
    }

    @Async
    public CompletableFuture<ResponseEntity<String>> doHTTPRequestAsync(final HttpRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return restTemplate.exchange(request.getUrl(), request.getMethod(), request.getHttpEntity(), String.class);
            } catch (HttpClientErrorException e) {
                throw new ApiClientException("Client Send Request Exception: " + e.getMessage(), e);
            }
        });
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, "application/json");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return headers;
    }

    private String objectToJson(Object requestData) throws Exception {
        return new ObjectMapper().writeValueAsString(requestData);
    }
}