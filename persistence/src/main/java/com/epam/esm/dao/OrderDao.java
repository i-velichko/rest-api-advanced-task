package com.epam.esm.dao;

import com.epam.esm.entity.Order;

import java.util.List;

/**
 * @author Ivan Velichko
 * @date 25.10.2021 20:05
 */

public interface OrderDao extends BaseDao<Order> {
    List<Order> findAllBy(long userId);

    long countQuery();
}
