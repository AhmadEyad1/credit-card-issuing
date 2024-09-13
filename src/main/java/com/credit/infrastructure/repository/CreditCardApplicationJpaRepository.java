package com.credit.infrastructure.repository;

import com.credit.domain.entity.CreditCardApplication;
import com.credit.domain.repository.CreditCardApplicationRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Repository
public class CreditCardApplicationJpaRepository extends SimpleJpaRepository<CreditCardApplication, Long> implements CreditCardApplicationRepository {

    private final EntityManager entityManager;

    public CreditCardApplicationJpaRepository(final EntityManager entityManager) {
        super(CreditCardApplication.class, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CreditCardApplication save(final CreditCardApplication application) {
        return super.save(application);
    }
}