package com.credit.domain.repository;

import com.credit.domain.entity.CreditCardApplicationStatus;

import java.util.Optional;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
public interface CreditCardApplicationStatusRepository {

    Optional<CreditCardApplicationStatus> getStatusById(final long id);
}
