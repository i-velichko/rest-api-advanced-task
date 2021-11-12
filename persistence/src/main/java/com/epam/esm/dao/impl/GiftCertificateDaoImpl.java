package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.query.QueryBuilder;
import com.epam.esm.entity.CertificateSearchParams;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
    private final QueryBuilder queryBuilder;

    @Autowired
    public GiftCertificateDaoImpl(QueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
    }

    @Override
    public List<GiftCertificate> findAll(Pagination pagination) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> certificate = criteriaQuery.from(GiftCertificate.class);
        criteriaQuery.select(certificate);
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(pagination.getPageNumber())
                .setMaxResults(pagination.getPageSize())
                .getResultList();
    }

    @Override
    public List<GiftCertificate> findAllBy(Pagination pagination, CertificateSearchParams searchParams) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = queryBuilder.createCriteriaQuery(searchParams, criteriaBuilder);
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(pagination.getPageNumber())
                .setMaxResults(pagination.getPageSize())
                .getResultList();
    }

    @Override
    public List<GiftCertificate> findAllBy(long orderId) {
        final String ORDER_ID = "orderId";
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> root = criteriaQuery.from(GiftCertificate.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get(ORDER_ID), orderId));
        criteriaQuery.select(root);
        List<GiftCertificate> resultList = entityManager.createQuery(criteriaQuery).getResultList();
        return resultList;
    }

    @Override
    public GiftCertificate update(GiftCertificate certificate) {
        return entityManager.merge(certificate);
    }

    @Override
    public GiftCertificate create(GiftCertificate certificate) {
        entityManager.persist(certificate);
        return entityManager.find(GiftCertificate.class, certificate.getId());
    }

    @Override
    public Optional<GiftCertificate> findBy(long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public void delete(long id) {
        entityManager.remove(entityManager.find(GiftCertificate.class, id));
    }
}
