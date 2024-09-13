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
public class CreditCardVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean identityVerified;

    @Column(nullable = false)
    private boolean complianceCheckPassed;

    @Column(nullable = false)
    private boolean employmentVerified;

    @Column(nullable = false)
    private double riskEvaluationScore;

    @Column(nullable = false)
    private double behavioralAnalysisScore;

    public double calculateVerificationScorePercentage() {
        if (!identityVerified) {
            return 0.0;
        }

        final double score = 20
                + (complianceCheckPassed ? 20 : 0)
                + (employmentVerified ? 20 : 0)
                + riskEvaluationScore / 5
                + behavioralAnalysisScore / 5;

        return Math.min(score, 100);
    }
}