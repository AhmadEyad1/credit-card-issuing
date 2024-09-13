package com.credit.infrastructure.client.creditCardProcessing;

import com.credit.application.client.CreditCardProcessingClient;
import com.credit.application.dto.CreditCardApplicationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Component
@Slf4j
public class CreditCardProcessingApiClientMock implements CreditCardProcessingClient {

    @Override
    public void process(final CreditCardApplicationDto application) {
        log.info("Calling Credit Card Processing Client API");
    }
}