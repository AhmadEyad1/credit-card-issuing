package com.credit.domain.usecase;

import com.credit.domain.entity.CreditCardApplicationStatus;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
public interface CreditCardApplicationStatusUsecase {

    CreditCardApplicationStatus getStatusById(final long id);
}