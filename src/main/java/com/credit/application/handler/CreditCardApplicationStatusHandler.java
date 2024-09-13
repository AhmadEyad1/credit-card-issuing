package com.credit.application.handler;

import com.credit.application.dto.CreditCardApplicationDto;
import com.credit.application.event.BackOfficeProcessingEvent;
import com.credit.application.event.CreditCardProcessingEvent;
import com.credit.application.mapper.CreditCardApplicationMapper;
import com.credit.domain.entity.CreditCardApplication;
import com.credit.domain.entity.CreditCardApplicationStatus;
import com.credit.domain.usecase.CreditCardApplicationStatusUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Service
@RequiredArgsConstructor
public class CreditCardApplicationStatusHandler {

    private final CreditCardApplicationStatusUsecase usecase;

    private final ApplicationEventPublisher eventPublisher;

    private final CreditCardApplicationMapper mapper;

    public void handle(final CreditCardApplication application) {
        final CreditCardApplicationStatus status = usecase.getStatusById(getStatusIdForScore(application.getVerification().calculateVerificationScorePercentage()));
        application.setStatus(status);

        fireEventsBasedOnStatus(status, application);
    }

    private int getStatusIdForScore(final double score) {
        return score >= 90 ? CreditCardApplicationStatus.STP
                : score >= 75 ? CreditCardApplicationStatus.NEAR_STP
                : score >= 50 ? CreditCardApplicationStatus.MANUAL_REVIEW
                : CreditCardApplicationStatus.REJECTED;
    }

    private void fireEventsBasedOnStatus(final CreditCardApplicationStatus status, final CreditCardApplication application) {
        final CreditCardApplicationDto applicationDto = mapper.toApplicationDto(application);

        if (status.isInStatus(CreditCardApplicationStatus.STP, CreditCardApplicationStatus.NEAR_STP)) {
            eventPublisher.publishEvent(new CreditCardProcessingEvent(this, applicationDto));
        }

        if (status.isInStatus(CreditCardApplicationStatus.NEAR_STP, CreditCardApplicationStatus.MANUAL_REVIEW)) {
            eventPublisher.publishEvent(new BackOfficeProcessingEvent(this, applicationDto));
        }
    }
}