package com.credit.application.client;

import com.credit.domain.entity.CreditCardApplicant;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
public interface EmploymentVerificationClient {

    boolean isVerified(final CreditCardApplicant applicant) throws JsonProcessingException;
}