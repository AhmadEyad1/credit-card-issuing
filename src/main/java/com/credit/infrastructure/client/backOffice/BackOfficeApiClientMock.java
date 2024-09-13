package com.credit.infrastructure.client.backOffice;

import com.credit.application.client.BackOfficeClient;
import com.credit.application.dto.CreditCardApplicationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Component
@Slf4j
public class BackOfficeApiClientMock implements BackOfficeClient {

    @Override
    public void handle(final CreditCardApplicationDto application) {
        log.info("Calling Back-office Client API");
    }
}
