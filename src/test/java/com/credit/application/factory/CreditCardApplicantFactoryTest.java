package com.credit.application.factory;

import com.credit.application.dto.CreditCardApplicationRequest;
import com.credit.domain.entity.CreditCardApplicant;
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
public class CreditCardApplicantFactoryTest {

    @Autowired
    private CreditCardApplicantFactory creditCardApplicantFactory;

    @Test
    public void shouldCreateCreditCardApplicant() {
        final CreditCardApplicationRequest request = CreditCardApplicationRequest.builder()
                .emiratesIdNumber("Ua12321")
                .name("Test name")
                .mobileNumber("+000501234567")
                .nationality("nationality")
                .address("Example Address")
                .income(15000.0)
                .employer("Company")
                .employmentStatus("Employed")
                .requestedCreditLimit(5000.00)
                .bankStatement(null)
                .build();

        final CreditCardApplicant applicant = creditCardApplicantFactory.create(request);

        assertEquals(request.getEmiratesIdNumber(), applicant.getPersonalIdentityNumber(), "Personal Identity Number mismatch");
        assertEquals(request.getName(), applicant.getName(), "Name mismatch");
        assertEquals(request.getMobileNumber(), applicant.getMobileNumber(), "Mobile Number mismatch");
        assertEquals(request.getNationality(), applicant.getNationality(), "Nationality mismatch");
        assertEquals(request.getAddress(), applicant.getResidentialAddress(), "Residential Address mismatch");
        assertEquals(request.getIncome(), applicant.getAnnualIncome(), "Annual Income mismatch");
        assertEquals(request.getEmployer(), applicant.getEmployer(), "Employer mismatch");
        assertEquals(request.getEmploymentStatus(), applicant.getEmploymentStatus(), "Employment Status mismatch");
    }
}
