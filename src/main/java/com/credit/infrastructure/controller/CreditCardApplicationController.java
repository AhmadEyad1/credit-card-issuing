package com.credit.infrastructure.controller;

import com.credit.application.dto.CreditCardApplicationRequest;
import com.credit.application.dto.CreditCardApplicationResponse;
import com.credit.application.facade.CreditCardApplicationFacade;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/credit-card/application")
public class CreditCardApplicationController {

    private final CreditCardApplicationFacade creditCardApplicationFacade;

    @PostMapping(value = "/apply", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CreditCardApplicationResponse> apply(@Valid @NotNull final CreditCardApplicationRequest request) throws IOException {
        return ResponseEntity.ok(creditCardApplicationFacade.apply(request));
    }
}