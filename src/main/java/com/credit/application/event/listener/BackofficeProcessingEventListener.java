package com.credit.application.event.listener;

import com.credit.application.client.BackOfficeClient;
import com.credit.application.event.BackOfficeProcessingEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Component
@RequiredArgsConstructor
public class BackofficeProcessingEventListener implements ApplicationListener<BackOfficeProcessingEvent> {

    private final BackOfficeClient client;

    @Override
    @Async
    public void onApplicationEvent(final BackOfficeProcessingEvent event) {
        client.handle(event.getApplication());
    }
}
