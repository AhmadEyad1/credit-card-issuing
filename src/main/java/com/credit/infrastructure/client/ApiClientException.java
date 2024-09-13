package com.credit.infrastructure.client;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
public class ApiClientException extends RuntimeException {

    public ApiClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
