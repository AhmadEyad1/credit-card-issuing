package com.credit.application.event;

import com.credit.application.dto.CreditCardApplicationDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Getter
public class CreditCardProcessingEvent extends ApplicationEvent {

    private final CreditCardApplicationDto application;


    public CreditCardProcessingEvent(final Object source, final CreditCardApplicationDto application) {
        super(source);
        this.application = application;
    }
}
