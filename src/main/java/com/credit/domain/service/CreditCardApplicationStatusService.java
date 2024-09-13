package com.credit.domain.service;

import com.credit.domain.entity.CreditCardApplicationStatus;
import com.credit.domain.repository.CreditCardApplicationStatusRepository;
import com.credit.domain.usecase.CreditCardApplicationStatusUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Service
@RequiredArgsConstructor
public class CreditCardApplicationStatusService implements CreditCardApplicationStatusUsecase {

    private final CreditCardApplicationStatusRepository applicationStatusRepository;

    @Override
    public CreditCardApplicationStatus getStatusById(final long id) {
        final Optional<CreditCardApplicationStatus> status = applicationStatusRepository.getStatusById(id);
        if (status.isEmpty()) {
            throw new IllegalArgumentException(String.format("Invalid argument: can't find status with ID %d", id));
        }

        return status.get();
    }
}
