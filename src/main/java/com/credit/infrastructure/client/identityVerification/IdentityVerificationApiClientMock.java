package com.credit.infrastructure.client.identityVerification;

import com.credit.application.client.IdentityVerificationClient;
import com.credit.domain.entity.CreditCardApplicant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Component
@Slf4j
public class IdentityVerificationApiClientMock implements IdentityVerificationClient {

    @Override
    public boolean isVerified(final CreditCardApplicant applicant) {
        log.info("Calling Identity Verification Client API");
        return true;
    }
}