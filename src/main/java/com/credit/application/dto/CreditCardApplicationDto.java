package com.credit.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditCardApplicationDto {

    @Valid
    @NotNull
    @JsonProperty("applicationId")
    private Long id;

    @Valid
    @NotNull
    @JsonProperty("status")
    private String status;

    @Valid
    @NotNull
    @JsonProperty("personalIdentityNumber")
    private String personalIdentityNumber;

    @Valid
    @NotNull
    @JsonProperty("name")
    private String name;

    @Valid
    @NotNull
    @JsonProperty("mobileNumber")
    private String mobileNumber;

    @Valid
    @NotNull
    @JsonProperty("nationality")
    private String nationality;

    @Valid
    @NotNull
    @JsonProperty("residentialAddress")
    private String residentialAddress;

    @Valid
    @NotNull
    @JsonProperty("annualIncome")
    private double annualIncome;

    @Valid
    @NotNull
    @JsonProperty("employer")
    private String employer;

    @Valid
    @NotNull
    @JsonProperty("employmentStatus")
    private String employmentStatus;

    @Valid
    @NotNull
    @JsonProperty("requestedCreditLimit")
    private double requestedCreditLimit;

    @Valid
    @NotNull
    @JsonProperty("bankStatementUrl")
    private String bankStatementUrl;

    @Valid
    @NotNull
    @JsonProperty("createdAt")
    private Instant createdAt;

    @Valid
    @NotNull
    @JsonProperty("verification")
    private CreditCardVerificationDto verification;
}