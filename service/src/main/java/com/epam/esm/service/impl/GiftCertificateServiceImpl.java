package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ivan Velichko
 * @date 28.10.2021 11:33
 */

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;
    private final GiftCertificateMapper certificateMapper;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, GiftCertificateMapper certificateMapper) {
        this.giftCertificateDao = giftCertificateDao;
        this.certificateMapper = certificateMapper;
    }

    @Override
    public List<GiftCertificateDto> findAll() {
        return certificateMapper.certificatesToCertificateDtoList(giftCertificateDao.findAll());
    }
}
