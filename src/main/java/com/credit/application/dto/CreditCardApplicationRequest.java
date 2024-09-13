package com.credit.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditCardApplicationRequest {

    @Valid
    @NotNull
    @JsonProperty("emiratesIdNumber")
    private String emiratesIdNumber;

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
    @JsonProperty("address")
    private String address;

    @Valid
    @NotNull
    @JsonProperty("income")
    private Double income;

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
    private Double requestedCreditLimit;

    @Valid
    @NotNull
    @JsonProperty("bankStatement")
    private MultipartFile bankStatement;
}