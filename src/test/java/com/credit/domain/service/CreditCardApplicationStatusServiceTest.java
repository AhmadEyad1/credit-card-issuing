package com.credit.domain.service;

import com.credit.domain.entity.CreditCardApplicationStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@SpringBootTest
@ActiveProfiles("test")
public class CreditCardApplicationStatusServiceTest {

    @Autowired
    private CreditCardApplicationStatusService creditCardApplicationStatusService;

    @Test
    void getValidApplicationStatus() {
        final CreditCardApplicationStatus status = creditCardApplicationStatusService.getStatusById(CreditCardApplicationStatus.MANUAL_REVIEW);
        assertNotNull(status, "Status not found");
        assertEquals(CreditCardApplicationStatus.MANUAL_REVIEW, status.getId(), "Status mismatch");
        assertEquals("MANUAL_REVIEW", status.getKey(), "Status key mismatch");
        assertEquals("Manual Review", status.getName(), "Status name mismatch");
    }

    @Test
    void getInvalidApplicationStatus() {
        final IllegalArgumentException thrownException = assertThrows(IllegalArgumentException.class, () -> {
            creditCardApplicationStatusService.getStatusById(-1);
        });

        assertEquals("Invalid argument: can't find status with ID -1", thrownException.getMessage(), "Message mismatch");
    }
}
