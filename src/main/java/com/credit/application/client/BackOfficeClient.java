package com.credit.application.client;

import com.credit.application.dto.CreditCardApplicationDto;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
public interface BackOfficeClient {

    void handle(final CreditCardApplicationDto application);
}