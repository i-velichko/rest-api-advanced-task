package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
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
 * @date 31.10.2021 12:43
 */
@Repository
public class OrderDaoImpl implements OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    public OrderDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Order> findAll(Pagination pagination) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> order = criteriaQuery.from(Order.class);
        return entityManager.createQuery(criteriaQuery.select(order)).getResultList();
    }

    @Override
    public List<Order> findAllBy(long userId) {
        final String USER_PARAM = "user";
        final String ID_PARAM = "id";
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = criteriaQuery.from(Order.class);
        criteriaQuery.select(root);

        Join<User, Order> userOrderJoin = root.join(USER_PARAM);
        criteriaQuery.where(criteriaBuilder.equal(userOrderJoin.get(ID_PARAM), userId));
        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    @Override
    public long countQuery() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Order.class)));
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    @Override
    public Order create(Order order) {
        return null;
    }

    @Override
    public Optional<Order> findBy(long id) {
        return Optional.empty();
    }

    @Override
    public void delete(long id) {

    }
}
