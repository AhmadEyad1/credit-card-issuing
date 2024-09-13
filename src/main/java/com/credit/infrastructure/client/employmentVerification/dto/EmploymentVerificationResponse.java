package com.credit.infrastructure.client.employmentVerification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentVerificationResponse {

    private boolean verified;
}
