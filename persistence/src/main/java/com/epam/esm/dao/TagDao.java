package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

/**
 * @author Ivan Velichko
 * @date 25.10.2021 20:04
 */
public interface TagDao extends BaseDao<Tag> {
    Tag findOrCreateTag(Tag tag);

    long countQuery();
}
