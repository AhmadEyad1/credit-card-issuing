package com.credit.application.mapper;

import com.credit.application.dto.CreditCardApplicationDto;
import com.credit.application.dto.CreditCardVerificationDto;
import com.credit.domain.entity.CreditCardApplication;
import org.springframework.stereotype.Component;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Component
public class CreditCardApplicationMapper {

    public CreditCardApplicationDto toApplicationDto(final CreditCardApplication application) {
        final CreditCardVerificationDto verificationDto = CreditCardVerificationDto.builder()
                .identityVerified(application.getVerification().isIdentityVerified())
                .complianceCheckPassed(application.getVerification().isComplianceCheckPassed())
                .employmentVerified(application.getVerification().isEmploymentVerified())
                .riskEvaluationScore(application.getVerification().getRiskEvaluationScore())
                .behavioralAnalysisScore(application.getVerification().getBehavioralAnalysisScore())
                .verificationScorePercentage(application.getVerification().calculateVerificationScorePercentage())
                .build();

        return CreditCardApplicationDto.builder()
                .id(application.getId())
                .status(application.getStatus().getName())
                .personalIdentityNumber(application.getApplicant().getPersonalIdentityNumber())
                .name(application.getApplicant().getName())
                .mobileNumber(application.getApplicant().getMobileNumber())
                .nationality(application.getApplicant().getNationality())
                .residentialAddress(application.getApplicant().getResidentialAddress())
                .annualIncome(application.getApplicant().getAnnualIncome())
                .employer(application.getApplicant().getEmployer())
                .employmentStatus(application.getApplicant().getEmploymentStatus())
                .requestedCreditLimit(application.getApplicationDetails().getRequestedCreditLimit())
                .bankStatementUrl(application.getApplicationDetails().getBankStatementUrl())
                .createdAt(application.getDateCreated())
                .verification(verificationDto)
                .build();
    }
}