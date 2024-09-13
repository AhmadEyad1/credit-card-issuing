package com.credit.domain.repository;

import com.credit.domain.entity.CreditCardApplication;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
public interface CreditCardApplicationRepository {

    CreditCardApplication save(final CreditCardApplication application);
}
