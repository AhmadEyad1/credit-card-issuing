package com.credit.infrastructure.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HttpRequest {

    private HttpMethod method;
    private String url;
    private HttpHeaders headers;
    private String body;
    private Map<String, String> queryParams;

    public HttpEntity<String> getHttpEntity() {
        return new HttpEntity<>(body, headers);
    }

    public String getUrl() {
        return buildUrlWithQueryParams();
    }

    private String buildUrlWithQueryParams() {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        if (queryParams != null) {
            queryParams.forEach(builder::queryParam);
        }
        return builder.toUriString();
    }
}
