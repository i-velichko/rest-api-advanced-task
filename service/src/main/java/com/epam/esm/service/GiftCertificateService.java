package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;

import java.util.List;

/**
 * @author Ivan Velichko
 * @date 28.10.2021 11:33
 */
public interface GiftCertificateService {
    List<GiftCertificateDto> findAll();
}
