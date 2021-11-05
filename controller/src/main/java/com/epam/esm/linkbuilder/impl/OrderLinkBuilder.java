package com.epam.esm.linkbuilder.impl;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    private void addCertificateIdLink(Class<?> controllerClass, OrderDto orderDto, long id, String linkName) {
        orderDto.add(linkTo(GiftCertificateController.class).slash(id).withRel(linkName));
    }
}
