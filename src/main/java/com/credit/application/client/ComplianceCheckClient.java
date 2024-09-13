package com.credit.application.client;

import com.credit.domain.entity.CreditCardApplicant;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
public interface ComplianceCheckClient {

    boolean isPassed(final CreditCardApplicant applicant) throws JsonProcessingException;
}
