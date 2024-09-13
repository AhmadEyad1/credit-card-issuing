package com.credit.infrastructure.client;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ApiClientTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ApiClient apiClient;

    @Test
    public void shouldPrepareHTTPRequest() {
        final Map<String, String> queryParams = Map.of("key", "value");

        final HttpRequest request = apiClient.prepareHTTPRequest(HttpMethod.GET, "http://example.com", null, queryParams);

        assertNotNull(request);
        assertEquals(HttpMethod.GET, request.getMethod());
        assertEquals("http://example.com?key=value", request.getUrl());
        assertTrue(Objects.requireNonNull(request.getHeaders().get(HttpHeaders.CONTENT_TYPE)).contains(MediaType.APPLICATION_JSON_VALUE));
        assertTrue(Objects.requireNonNull(request.getHeaders().get(HttpHeaders.ACCEPT)).contains(MediaType.APPLICATION_JSON_VALUE));
        assertEquals(queryParams, request.getQueryParams());
    }

    @Test
    public void shouldDoHTTPRequest() throws Exception {
        final HttpRequest request = new HttpRequest(HttpMethod.GET, "http://example.com", null, null, null);
        final ResponseEntity<String> responseEntity = new ResponseEntity<>("response", HttpStatus.OK);

        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class)))
                .thenReturn(responseEntity);

        final ResponseEntity<String> response = apiClient.doHTTPRequest(request);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("response", response.getBody());

        verify(restTemplate).exchange(eq(request.getUrl()), eq(request.getMethod()), any(HttpEntity.class), eq(String.class));
    }

    @Test
    public void shouldThrowApiClientExceptionOnDoHTTPRequest() {
        final HttpRequest request = new HttpRequest(HttpMethod.GET, "http://example.com", null, null, null);

        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        assertThrows(ApiClientException.class, () -> apiClient.doHTTPRequest(request));
    }

    @Test
    public void shouldDoHTTPRequestAsync() throws Exception {
        final HttpRequest request = new HttpRequest(HttpMethod.GET, "http://example.com", null, null, null);
        final ResponseEntity<String> responseEntity = new ResponseEntity<>("response", HttpStatus.OK);

        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class)))
                .thenReturn(responseEntity);

        final CompletableFuture<ResponseEntity<String>> futureResponse = apiClient.doHTTPRequestAsync(request);

        final ResponseEntity<String> response = futureResponse.get();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("response", response.getBody());

        verify(restTemplate).exchange(eq(request.getUrl()), eq(request.getMethod()), any(HttpEntity.class), eq(String.class));
    }
}