package com.credit.infrastructure.client.employmentVerification;

import com.credit.application.client.EmploymentVerificationClient;
import com.credit.domain.entity.CreditCardApplicant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Component
@Slf4j
public class EmploymentVerificationApiClientMock implements EmploymentVerificationClient {

    @Override
    public boolean isVerified(final CreditCardApplicant applicant) {
        log.info("Calling Employment Verification Client API");
        return true;
    }
}