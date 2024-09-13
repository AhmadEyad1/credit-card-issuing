package com.credit.application.mapper;

import com.credit.application.dto.CreditCardApplicationDto;
import com.credit.application.dto.CreditCardVerificationDto;
import com.credit.domain.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@SpringBootTest
@ActiveProfiles("test")
public class CreditCardApplicationMapperTest {

    @Autowired
    private CreditCardApplicationMapper creditCardApplicationMapper;

    @Test
    public void shouldMapCreditCardApplicationToDto() {
        final CreditCardApplicant applicant = CreditCardApplicant.builder()
                .personalIdentityNumber("123456789")
                .name("John Doe")
                .mobileNumber("+123456789")
                .nationality("US")
                .residentialAddress("123 Main St")
                .annualIncome(50000)
                .employer("Company Inc.")
                .employmentStatus("Employed")
                .build();

        final CreditCardApplicationDetails applicationDetails = CreditCardApplicationDetails.builder()
                .requestedCreditLimit(10000)
                .bankStatementUrl("http://example.com/statement")
                .build();

        final CreditCardVerification verification = CreditCardVerification.builder()
                .identityVerified(true)
                .complianceCheckPassed(true)
                .employmentVerified(true)
                .riskEvaluationScore(80.0)
                .behavioralAnalysisScore(85.0)
                .build();

        final CreditCardApplicationStatus status = CreditCardApplicationStatus.builder()
                .id(1)
                .key("STP")
                .name("Stp")
                .build();

        final Instant dateCreated = Instant.now();
        final CreditCardApplication application = CreditCardApplication.builder()
                .applicant(applicant)
                .applicationDetails(applicationDetails)
                .verification(verification)
                .status(status)
                .dateCreated(dateCreated)
                .build();

        final CreditCardApplicationDto applicationDto = creditCardApplicationMapper.toApplicationDto(application);

        assertEquals("Stp", applicationDto.getStatus());
        assertEquals("123456789", applicationDto.getPersonalIdentityNumber());
        assertEquals("John Doe", applicationDto.getName());
        assertEquals("+123456789", applicationDto.getMobileNumber());
        assertEquals("US", applicationDto.getNationality());
        assertEquals("123 Main St", applicationDto.getResidentialAddress());
        assertEquals(50000, applicationDto.getAnnualIncome());
        assertEquals("Company Inc.", applicationDto.getEmployer());
        assertEquals("Employed", applicationDto.getEmploymentStatus());
        assertEquals(10000, applicationDto.getRequestedCreditLimit());
        assertEquals("http://example.com/statement", applicationDto.getBankStatementUrl());
        assertEquals(dateCreated, applicationDto.getCreatedAt());

        final CreditCardVerificationDto verificationDto = applicationDto.getVerification();
        assertNotNull(verificationDto);
        assertTrue(verificationDto.isIdentityVerified());
        assertTrue(verificationDto.isComplianceCheckPassed());
        assertTrue(verificationDto.isEmploymentVerified());
        assertEquals(80.0, verificationDto.getRiskEvaluationScore(), 0.01);
        assertEquals(85.0, verificationDto.getBehavioralAnalysisScore(), 0.01);
        assertEquals(verification.calculateVerificationScorePercentage(), verificationDto.getVerificationScorePercentage(), 0.01);
    }
}