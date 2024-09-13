package com.credit.infrastructure.repository;

import com.credit.domain.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@SpringBootTest
@ActiveProfiles("test")
public class CreditCardApplicationJpaRepositoryTest {

    @Autowired
    private CreditCardApplicationJpaRepository creditCardApplicationJpaRepository;

    @Autowired
    private CreditCardApplicationStatusJpaRepository creditCardApplicationStatusJpaRepository;

    @Test
    void save() {
        final CreditCardApplicant creditCardApplicant = CreditCardApplicant.builder()
                .personalIdentityNumber("123Q2")
                .name("Test Name")
                .mobileNumber("+000123456789")
                .nationality("Nationality")
                .residentialAddress("Address")
                .annualIncome(150000)
                .employer("Testing LTC")
                .employmentStatus("Employed")
                .build();

        final CreditCardVerification verification = CreditCardVerification.builder()
                .identityVerified(true)
                .complianceCheckPassed(true)
                .employmentVerified(true)
                .riskEvaluationScore(98)
                .behavioralAnalysisScore(100)
                .build();

        final Optional<CreditCardApplicationStatus> status = creditCardApplicationStatusJpaRepository.getStatusById(1);
        assertTrue(status.isPresent(), "Status not found");

        final CreditCardApplicationDetails applicationDetails = CreditCardApplicationDetails.builder()
                .requestedCreditLimit(5000)
                .bankStatementUrl("https://bucket.s3.amazonaws.com/test_file")
                .build();

        final CreditCardApplication application = CreditCardApplication.builder()
                .applicant(creditCardApplicant)
                .verification(verification)
                .status(status.get())
                .applicationDetails(applicationDetails)
                .dateCreated(Instant.now())
                .dateUpdated(Instant.now())
                .build();

        final CreditCardApplication savedApplication = creditCardApplicationJpaRepository.save(application);
        assertNotNull(savedApplication, "Application is not saved");
        assertEquals(creditCardApplicant, savedApplication.getApplicant(), "Applicant is not saved");
        assertEquals(verification, savedApplication.getVerification(), "Verification is not saved");
        assertEquals(status.get(), savedApplication.getStatus(), "Status is not saved");
        assertEquals(applicationDetails, savedApplication.getApplicationDetails(), "ApplicationDetails is not saved");
        assertEquals(application.getDateCreated(), savedApplication.getDateCreated(), "Date is not saved");
        assertEquals(application.getDateUpdated(), savedApplication.getDateUpdated(), "Date is not saved");
    }
}