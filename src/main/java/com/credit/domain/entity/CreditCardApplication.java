package com.credit.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditCardApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "applicant_id", nullable = false)
    private CreditCardApplicant applicant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false)
    private CreditCardApplicationStatus status;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "verification_id", nullable = false)
    private CreditCardVerification verification;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "application_details_id", nullable = false)
    private CreditCardApplicationDetails applicationDetails;

    @Column(nullable = false)
    private Instant dateCreated;

    @Column
    private Instant dateUpdated;
}