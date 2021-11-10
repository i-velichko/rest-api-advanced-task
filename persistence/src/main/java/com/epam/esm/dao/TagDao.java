package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.Optional;

/**
 * @author Ivan Velichko
 * @date 25.10.2021 20:04
 */
public interface TagDao extends BaseDao<Tag> {
    Tag findOrCreateTag(Tag tag);
    Optional<Tag> findBy(String name);
    Optional<Tag> findMostUsersWidelyUsedTag(long userId);
}
