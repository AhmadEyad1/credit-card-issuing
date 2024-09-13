package com.credit.infrastructure.client.identityVerification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdentityVerificationResponse {

    private boolean verified;
}
