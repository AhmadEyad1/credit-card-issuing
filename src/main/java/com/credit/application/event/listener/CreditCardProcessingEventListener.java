package com.credit.application.event.listener;

import com.credit.application.client.CreditCardProcessingClient;
import com.credit.application.event.CreditCardProcessingEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Component
@RequiredArgsConstructor
public class CreditCardProcessingEventListener implements ApplicationListener<CreditCardProcessingEvent> {

    private final CreditCardProcessingClient client;

    @Override
    @Async
    public void onApplicationEvent(final CreditCardProcessingEvent event) {
        client.process(event.getApplication());
    }
}
