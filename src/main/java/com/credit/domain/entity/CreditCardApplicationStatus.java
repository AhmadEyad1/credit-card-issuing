package com.credit.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditCardApplicationStatus {

    public static int STP = 1;
    public static int NEAR_STP = 2;
    public static int MANUAL_REVIEW = 3;
    public static int REJECTED = 4;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "`key`", nullable = false, length = 32)
    private String key;

    @Column(nullable = false, length = 32)
    private String name;

    public boolean isInStatus(int... statuses) {
        return this.id != null && Arrays.stream(statuses).anyMatch(status -> status == this.id);
    }
}