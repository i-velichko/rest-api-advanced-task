package com.epam.esm.linkbuilder.impl;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.dto.GiftCertificateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * @author Ivan Velichko
 * @date 03.11.2021 12:24
 */
@Component
public class CertificateLinkBuilder extends AbstractLinkBuilder<GiftCertificateDto> {

    private final TagLinkBuilder tagLinkBuilder;

    @Autowired
    public CertificateLinkBuilder(TagLinkBuilder tagLinkBuilder) {
        this.tagLinkBuilder = tagLinkBuilder;
    }

    @Override
    public void addLinks(GiftCertificateDto giftCertificateDto) {
        addIdLinks(GiftCertificateController.class, giftCertificateDto, giftCertificateDto.getId(), SELF, UPDATE, DELETE);
        if (!CollectionUtils.isEmpty(giftCertificateDto.getTags())) {
            giftCertificateDto.getTags().forEach(tagLinkBuilder::addLinks);
        }
    }
}
