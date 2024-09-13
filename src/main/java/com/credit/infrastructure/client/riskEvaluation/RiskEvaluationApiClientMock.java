package com.credit.infrastructure.client.riskEvaluation;

import com.credit.application.client.RiskEvaluationClient;
import com.credit.domain.entity.CreditCardApplicant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Component
@Slf4j
public class RiskEvaluationApiClientMock implements RiskEvaluationClient {

    @Override
    public double evaluate(final CreditCardApplicant applicant) {
        log.info("Calling Risk Evaluation Client API");
        return 90;
    }
}