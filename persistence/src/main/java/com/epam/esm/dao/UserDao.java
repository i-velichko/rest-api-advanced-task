package com.epam.esm.dao;

import com.epam.esm.entity.User;

/**
 * @author Ivan Velichko
 * @date 25.10.2021 20:04
 */
public interface UserDao extends BaseDao<User> {
    long countQuery();

    User update(User user);

    boolean isPresent(long id);
}
