package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * @author Ivan Velichko
 * @date 28.10.2021 11:20
 */
@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    @PersistenceContext
    private EntityManager entityManager;

    public GiftCertificateDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<GiftCertificate> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> certificate = criteriaQuery.from(GiftCertificate.class);
        criteriaQuery.select(certificate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public GiftCertificate create(GiftCertificate certificate) {
        return null;
    }

    @Override
    public Optional<GiftCertificate> findBy(long id) {
        return Optional.empty();
    }

    @Override
    public void delete(long id) {

    }
}
