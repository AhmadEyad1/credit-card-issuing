package com.credit.application.factory;

import com.credit.application.dto.CreditCardApplicationRequest;
import com.credit.domain.entity.CreditCardApplicant;
import org.springframework.stereotype.Service;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Service
public class CreditCardApplicantFactory {

    public CreditCardApplicant create(final CreditCardApplicationRequest request) {
        return CreditCardApplicant.builder()
                .personalIdentityNumber(request.getEmiratesIdNumber())
                .name(request.getName())
                .mobileNumber(request.getMobileNumber())
                .nationality(request.getNationality())
                .residentialAddress(request.getAddress())
                .annualIncome(request.getIncome())
                .employer(request.getEmployer())
                .employmentStatus(request.getEmploymentStatus())
                .build();
    }
}
