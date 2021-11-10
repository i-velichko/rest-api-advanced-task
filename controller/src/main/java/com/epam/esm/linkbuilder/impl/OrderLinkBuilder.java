package com.epam.esm.linkbuilder.impl;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * @author Ivan Velichko
 * @date 03.11.2021 18:45
 */
@Component
public class OrderLinkBuilder extends AbstractLinkBuilder<OrderDto> {
    private static final String CERTIFICATES = "certificates";
    private CertificateLinkBuilder certificateLinkBuilder;
    private GiftCertificateService giftCertificateService;


    @Autowired
    public OrderLinkBuilder(CertificateLinkBuilder certificateLinkBuilder, GiftCertificateService giftCertificateService) {
        this.certificateLinkBuilder = certificateLinkBuilder;
        this.giftCertificateService = giftCertificateService;
    }

    @Override
    public void addLinks(OrderDto orderDto) {
        addIdLinks(OrderController.class, orderDto, orderDto.getId(), SELF, UPDATE, DELETE);

    }

}
