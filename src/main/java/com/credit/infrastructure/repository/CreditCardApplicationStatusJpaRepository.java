package com.credit.infrastructure.repository;

import com.credit.domain.entity.CreditCardApplicationStatus;
import com.credit.domain.repository.CreditCardApplicationStatusRepository;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author ahmad.yousef <ahmadeyad.network@gmail.com>
 */
@Repository
public class CreditCardApplicationStatusJpaRepository extends SimpleJpaRepository<CreditCardApplicationStatus, Long> implements CreditCardApplicationStatusRepository {

    private final EntityManager entityManager;

    public CreditCardApplicationStatusJpaRepository(final EntityManager entityManager) {
        super(CreditCardApplicationStatus.class, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Optional<CreditCardApplicationStatus> getStatusById(final long id) {
        final String query = "SELECT ccsa FROM CreditCardApplicationStatus ccsa WHERE ccsa.id = :id";
        return entityManager.createQuery(query, CreditCardApplicationStatus.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }
}