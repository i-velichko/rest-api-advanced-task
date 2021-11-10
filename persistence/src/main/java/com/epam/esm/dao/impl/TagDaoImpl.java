package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * @author Ivan Velichko
 * @date 25.10.2021 20:39
 */
@Repository
public class TagDaoImpl implements TagDao {

    private static final String FIND_MOST_USERS_WIDELY_USED_TAG_SQL = "SELECT COUNT(t.id) as tagCount,\n" +
            "       t.id        ,\n" +
            "       t.name      ,\n" +
            "       mo.nickname as userNickname,\n" +
            "       orderID,\n" +
            "       mo.highestCost\n" +
            "FROM (SELECT u.nickname, o.id as orderID, MAX(o.cost) as highestCost\n" +
            "      FROM users as u\n" +
            "               LEFT JOIN orders o on u.id = o.user_id\n" +
            "      WHERE u.id = :userId) as mo\n" +
            "         LEFT JOIN gift_certificates gc on mo.orderID = gc.order_id\n" +
            "         LEFT JOIN certificates_tags ct on gc.id = ct.certificate_id\n" +
            "         LEFT JOIN tags t on ct.tag_id = t.id\n" +
            "GROUP BY t.id, t.name, mo.nickname, orderID, mo.highestCost\n" +
            "ORDER BY 1 DESC\n" +
            "LIMIT 1";
    private static final String USER_ID_PARAM = "userId";

    @PersistenceContext
    private EntityManager entityManager;

    public TagDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Tag> findAll(Pagination pagination) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> tag = criteriaQuery.from(Tag.class);
        criteriaQuery.select(tag);
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(pagination.getPageNumber())
                .setMaxResults(pagination.getPageSize())
                .getResultList();
    }

    @Override
    public Tag create(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public Tag findOrCreateTag(Tag tag) {
        return findBy(tag.getName()).orElseGet(() -> create(tag));
    }

    @Override
    public Optional<Tag> findBy(long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    public Optional<Tag> findBy(String name) {
        final String NAME_PARAM = "name";
        final int TAG_PARAM = 0;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = query.from(Tag.class);
        query.select(root);
        query.where(criteriaBuilder.equal(root.get(NAME_PARAM), name));
        List<Tag> tags = entityManager.createQuery(query).getResultList();
        return tags.size() == 0 ? Optional.empty() : Optional.of(tags.get(TAG_PARAM));
    }

    @Override
    public Optional<Tag> findMostUsersWidelyUsedTag(long userId) {
        Query query = entityManager.createNativeQuery(FIND_MOST_USERS_WIDELY_USED_TAG_SQL, Tag.class);
        query.setParameter(USER_ID_PARAM, userId);
        return Optional.of((Tag) query.getSingleResult());
    }

    @Override
    public void delete(long id) {
        entityManager.remove(entityManager.find(Tag.class, id));
    }

}
