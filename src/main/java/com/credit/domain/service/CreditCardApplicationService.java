package com.credit.domain.service;

import com.credit.domain.entity.CreditCardApplication;
import com.credit.domain.repository.CreditCardApplicationRepository;
import com.credit.domain.usecase.CreditCardApplicationUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Service
@RequiredArgsConstructor
public class CreditCardApplicationService implements CreditCardApplicationUsecase {

    private final CreditCardApplicationRepository applicationRepository;

    @Override
    public CreditCardApplication saveApplication(final CreditCardApplication application) {
        return applicationRepository.save(application);
    }
}
