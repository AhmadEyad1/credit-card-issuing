package com.credit.infrastructure.repository;

import com.credit.domain.entity.CreditCardApplicationStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@SpringBootTest
@ActiveProfiles("test")
public class CreditCardApplicationStatusJpaRepositoryTest {

    @Autowired
    private CreditCardApplicationStatusJpaRepository creditCardApplicationStatusJpaRepository;

    @Test
    void getStatusById() {
        final Optional<CreditCardApplicationStatus> status = creditCardApplicationStatusJpaRepository.getStatusById(CreditCardApplicationStatus.MANUAL_REVIEW);
        assertTrue(status.isPresent(), "Status not found");
        assertEquals(CreditCardApplicationStatus.MANUAL_REVIEW, status.get().getId(), "Status mismatch");
        assertEquals("MANUAL_REVIEW", status.get().getKey(), "Status key mismatch");
        assertEquals("Manual Review", status.get().getName(), "Status name mismatch");
    }
}
