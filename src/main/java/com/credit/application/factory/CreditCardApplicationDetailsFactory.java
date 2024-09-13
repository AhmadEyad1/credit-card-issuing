package com.credit.application.factory;

import com.credit.application.dto.CreditCardApplicationRequest;
import com.credit.domain.entity.CreditCardApplicationDetails;
import org.springframework.stereotype.Service;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Service
public class CreditCardApplicationDetailsFactory {

    public CreditCardApplicationDetails create(final CreditCardApplicationRequest request, final String bankStatementUrl) {
        return CreditCardApplicationDetails.builder()
                .requestedCreditLimit(request.getRequestedCreditLimit())
                .bankStatementUrl(bankStatementUrl)
                .build();
    }
}