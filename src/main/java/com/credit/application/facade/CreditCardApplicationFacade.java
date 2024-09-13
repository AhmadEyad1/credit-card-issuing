package com.credit.application.facade;

import com.credit.application.dto.CreditCardApplicationRequest;
import com.credit.application.dto.CreditCardApplicationResponse;
import com.credit.application.factory.CreditCardApplicantFactory;
import com.credit.application.factory.CreditCardApplicationDetailsFactory;
import com.credit.application.handler.CreditCardApplicationStatusHandler;
import com.credit.application.processor.CreditCardVerificationProcessor;
import com.credit.application.service.FileUploader;
import com.credit.domain.entity.*;
import com.credit.domain.usecase.CreditCardApplicationUsecase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Service
@RequiredArgsConstructor
public class CreditCardApplicationFacade {

    private final CreditCardApplicantFactory applicantFactory;

    private final CreditCardApplicationDetailsFactory applicationDetailsFactory;

    private final FileUploader fileUploader;

    private final CreditCardVerificationProcessor processor;

    private final CreditCardApplicationStatusHandler handler;

    private final CreditCardApplicationUsecase usecase;

    @Transactional
    public CreditCardApplicationResponse apply(final CreditCardApplicationRequest request) throws IOException {
        final String fileUrl = fileUploader.uploadFile(request.getBankStatement());

        final CreditCardApplicant applicant = applicantFactory.create(request);
        final CreditCardApplicationDetails applicationDetails = applicationDetailsFactory.create(request, fileUrl);

        final CreditCardVerification verification = processor.process(applicant, applicationDetails);

        final CreditCardApplication application = CreditCardApplication.builder()
                .applicant(applicant)
                .applicationDetails(applicationDetails)
                .verification(verification)
                .dateCreated(Instant.now())
                .build();

        handler.handle(application);
        usecase.saveApplication(application);

        return CreditCardApplicationResponse.builder()
                .status(application.getStatus().getName())
                .message(getResponseMessage(application.getStatus()))
                .build();
    }

    private String getResponseMessage(final CreditCardApplicationStatus status) {
        return status == null || status.getId() == null
                ? "Your application status is not available at the moment. Please try again later."
                : status.isInStatus(CreditCardApplicationStatus.STP)
                ? "Congratulations! Your credit card has been issued automatically. You should receive it soon."
                : status.isInStatus(CreditCardApplicationStatus.NEAR_STP)
                ? "Your credit card has been approved, but we are reviewing the credit limit. You will be notified once the review is complete."
                : status.isInStatus(CreditCardApplicationStatus.MANUAL_REVIEW)
                ? "Your application is under further review. Our team is assessing your request and will update you shortly."
                : status.isInStatus(CreditCardApplicationStatus.REJECTED)
                ? "We regret to inform you that your credit card application has been rejected. Please contact support for more information."
                : "Your application is being processed. Please check back for updates.";
    }
}