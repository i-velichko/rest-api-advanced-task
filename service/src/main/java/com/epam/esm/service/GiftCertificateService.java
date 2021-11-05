package com.epam.esm.service;

import com.epam.esm.dto.*;
import com.epam.esm.entity.CertificateSearchParams;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Pagination;

import java.util.List;
import java.util.Map;

/**
 * @author Ivan Velichko
 * @date 28.10.2021 11:33
 */
public interface GiftCertificateService {
    PageDto<GiftCertificateDto> findAllBy(Map<String, String> params);
    List<GiftCertificateDto> findAllBy(long orderId);
    GiftCertificateDto findBy(long id);
    GiftCertificateDto create(GiftCertificateDto giftCertificateDto);
    void delete(long id);

    GiftCertificateDto update(GiftCertificateDto giftCertificateDto);
}
