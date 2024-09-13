package com.credit.infrastructure.client.complianceCheck;

import com.credit.application.client.ComplianceCheckClient;
import com.credit.domain.entity.CreditCardApplicant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Component
@Slf4j
public class ComplianceCheckApiClientMock implements ComplianceCheckClient {

    @Override
    public boolean isPassed(final CreditCardApplicant applicant) {
        log.info("Calling Compliance Check Client API");
        return true;
    }
}
