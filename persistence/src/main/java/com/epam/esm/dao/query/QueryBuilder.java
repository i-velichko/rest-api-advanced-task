package com.epam.esm.dao.query;

import com.epam.esm.entity.CertificateSearchParams;
import com.epam.esm.entity.GiftCertificate;

import com.epam.esm.entity.SortingOrder;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ivan Velichko
 * @date 01.11.2021 13:48
 */
@Component
public class QueryBuilder {
    private static final String TAGS = "tags";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String PERCENT = "%";
    private static final String CREATE_DATE = "createDate";

    public CriteriaQuery<GiftCertificate> createCriteriaQuery(CertificateSearchParams searchParams, CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> giftCertificateRoot = criteriaQuery.from(GiftCertificate.class);
        List<Predicate> predicates = new ArrayList<>();

        if (searchParams.getTagNames() != null) {
            predicates.addAll(addTagNames(searchParams.getTagNames(), criteriaBuilder, giftCertificateRoot));
        }
        if (searchParams.getPartNameOrDescription() != null) {
            predicates.add(addPartNameOrDescription(searchParams.getPartNameOrDescription(), criteriaBuilder, giftCertificateRoot));
        }
        criteriaQuery.select(giftCertificateRoot).where(predicates.toArray(new Predicate[]{}));
        addSortType(searchParams, criteriaBuilder, criteriaQuery, giftCertificateRoot);
        return criteriaQuery;
    }

    private List<Predicate> addTagNames(List<String> tagNames, CriteriaBuilder criteriaBuilder, Root<GiftCertificate> giftCertificateRoot) {
        return tagNames.stream()
                .map(tagName -> criteriaBuilder.equal(giftCertificateRoot.join(TAGS).get(NAME), tagName))
                .collect(Collectors.toList());

    }

    private Predicate addPartNameOrDescription(String partNameOrDescription, CriteriaBuilder criteriaBuilder, Root<GiftCertificate> giftCertificateRoot) {
        return criteriaBuilder.or
                (criteriaBuilder.like(giftCertificateRoot.get(DESCRIPTION),
                                PERCENT + partNameOrDescription + PERCENT),
                        criteriaBuilder.like(giftCertificateRoot.get(NAME),
                                PERCENT + partNameOrDescription + PERCENT));

    }

    private void addSortType(CertificateSearchParams searchParams, CriteriaBuilder criteriaBuilder, CriteriaQuery<GiftCertificate> criteriaQuery, Root<GiftCertificate> giftCertificateRoot) {
        List<Order> orderList = new ArrayList<>();
        SortingOrder sortingOrderByDate = searchParams.getSortingOrderByDate();
        if (sortingOrderByDate != null) {
            Order order = (sortingOrderByDate == SortingOrder.ASC) ?
                    criteriaBuilder.asc(giftCertificateRoot.get(CREATE_DATE)) :
                    criteriaBuilder.desc(giftCertificateRoot.get(CREATE_DATE));
            orderList.add(order);
        }
        SortingOrder sortingOrderByName = searchParams.getSortingOrderByName();
        if (sortingOrderByName != null) {
            Order order = (sortingOrderByName == SortingOrder.ASC) ?
                    criteriaBuilder.asc(giftCertificateRoot.get(NAME)) :
                    criteriaBuilder.desc(giftCertificateRoot.get(NAME));
            orderList.add(order);
        }
        criteriaQuery.orderBy(orderList);
    }
}
