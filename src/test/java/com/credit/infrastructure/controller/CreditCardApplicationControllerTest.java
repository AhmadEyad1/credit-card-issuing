package com.credit.infrastructure.controller;

import com.credit.application.dto.CreditCardApplicationRequest;
import com.credit.application.dto.CreditCardApplicationResponse;
import com.credit.application.facade.CreditCardApplicationFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CreditCardApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreditCardApplicationFacade creditCardApplicationFacade;

    @Test
    void shouldApplyCreditCardApplication() throws Exception {
        final CreditCardApplicationResponse response = CreditCardApplicationResponse.builder()
                .status("STP")
                .message("STP Message")
                .build();

        when(creditCardApplicationFacade.apply(any(CreditCardApplicationRequest.class)))
                .thenReturn(response);

        mockMvc.perform(multipart("/api/v1/credit-card/application/apply")
                        .file("bankStatement", "test-file-content".getBytes())
                        .param("emiratesIdNumber", "U1231")
                        .param("name", "Test name")
                        .param("mobileNumber", "+000123456789")
                        .param("nationality", "nationality")
                        .param("address", "address")
                        .param("income", "150000")
                        .param("employer", "Company")
                        .param("employmentStatus", "Employed")
                        .param("requestedCreditLimit", "5000"))
                .andExpect(status().isOk())
                .andExpect(content().json("{status:'STP',message:'STP Message'}"));

        verify(creditCardApplicationFacade).apply(any(CreditCardApplicationRequest.class));
    }

    @Test
    void missingRequestParameter() throws Exception {
        // missing emiratesIdNumber
        mockMvc.perform(multipart("/api/v1/credit-card/application/apply")
                        .file("bankStatement", "test-file-content".getBytes())
                        .param("name", "Test name")
                        .param("mobileNumber", "+000123456789")
                        .param("nationality", "nationality")
                        .param("address", "address")
                        .param("income", "150000")
                        .param("employer", "Company")
                        .param("employmentStatus", "Employed")
                        .param("requestedCreditLimit", "5000"))
                .andExpect(status().isBadRequest());
    }
}
