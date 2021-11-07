package com.epam.esm.dao;

import com.epam.esm.entity.CertificateSearchParams;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Pagination;

import java.util.List;

/**
 * @author Ivan Velichko
 * @date 25.10.2021 20:05
 */
public interface GiftCertificateDao extends BaseDao<GiftCertificate> {

    List<GiftCertificate> findAllBy(Pagination pagination, CertificateSearchParams searchParams);

    List<GiftCertificate> findAllBy(long orderId);

    GiftCertificate update(GiftCertificate certificate);

}
