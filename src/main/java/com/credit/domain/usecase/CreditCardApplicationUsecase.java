package com.credit.domain.usecase;

import com.credit.domain.entity.CreditCardApplication;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
public interface CreditCardApplicationUsecase {

    CreditCardApplication saveApplication(final CreditCardApplication application);
}