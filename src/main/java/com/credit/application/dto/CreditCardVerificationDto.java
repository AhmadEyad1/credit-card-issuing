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

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditCardVerificationDto {

    @Valid
    @NotNull
    @JsonProperty("identityVerified")
    private boolean identityVerified;

    @Valid
    @NotNull
    @JsonProperty("complianceCheckPassed")
    private boolean complianceCheckPassed;

    @Valid
    @NotNull
    @JsonProperty("employmentVerified")
    private boolean employmentVerified;

    @Valid
    @NotNull
    @JsonProperty("riskEvaluationScore")
    private double riskEvaluationScore;

    @Valid
    @NotNull
    @JsonProperty("behavioralAnalysisScore")
    private double behavioralAnalysisScore;

    @Valid
    @NotNull
    @JsonProperty("verificationScorePercentage")
    private double verificationScorePercentage;
}