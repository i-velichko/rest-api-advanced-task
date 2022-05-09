package com.epam.esm.dao;

import com.epam.esm.entity.Pagination;

import java.util.List;
import java.util.Optional;

/**
 * @author Ivan Velichko
 * @date 25.10.2021 20:03
 */
public interface BaseDao<T> {

    List<T> findAll(Pagination pagination);

    T create(T t);

    Optional<T> findBy(long id);

    void delete(long id);
}
