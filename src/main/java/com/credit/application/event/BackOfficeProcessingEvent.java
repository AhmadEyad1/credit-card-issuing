package com.credit.application.event;

import com.credit.application.dto.CreditCardApplicationDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Getter
public class BackOfficeProcessingEvent extends ApplicationEvent {

    private final CreditCardApplicationDto application;

    public BackOfficeProcessingEvent(final Object source, final CreditCardApplicationDto application) {
        super(source);
        this.application = application;
    }
}
