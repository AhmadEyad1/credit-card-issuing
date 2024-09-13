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
public class CreditCardApplicationDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double requestedCreditLimit;

    @Column(nullable = false, length = 2083)
    private String bankStatementUrl;
}