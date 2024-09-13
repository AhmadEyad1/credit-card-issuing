package com.credit.application.factory;

import com.credit.application.dto.CreditCardApplicationRequest;
import com.credit.domain.entity.CreditCardApplicationDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@SpringBootTest
@ActiveProfiles("test")
public class CreditCardApplicationDetailsFactoryTest {

    @Autowired
    private CreditCardApplicationDetailsFactory creditCardApplicationDetailsFactory;

    @Test
    public void shouldCreateCreditCardApplicationDetails() {
        final CreditCardApplicationRequest request = CreditCardApplicationRequest.builder()
                .requestedCreditLimit(5000.00)
                .bankStatement(null)
                .build();

        final String bankStatementUrl = "https://example.com/bank-statement";

        final CreditCardApplicationDetails details = creditCardApplicationDetailsFactory.create(request, bankStatementUrl);

        assertEquals(request.getRequestedCreditLimit(), details.getRequestedCreditLimit(), "Requested Credit Limit mismatch");
        assertEquals(bankStatementUrl, details.getBankStatementUrl(), "Bank Statement URL mismatch");
    }
}