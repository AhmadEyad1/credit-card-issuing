package com.credit.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditCardApplicant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String personalIdentityNumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 20)
    private String mobileNumber;

    @Column(nullable = false, length = 50)
    private String nationality;

    @Column(nullable = false)
    private String residentialAddress;

    @Column(nullable = false)
    private double annualIncome;

    @Column(nullable = false)
    private String employer;

    @Column(nullable = false)
    private String employmentStatus;
}