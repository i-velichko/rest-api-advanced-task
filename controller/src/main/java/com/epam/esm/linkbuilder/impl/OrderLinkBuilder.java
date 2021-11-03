package com.epam.esm.linkbuilder.impl;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.OrderDto;
import org.springframework.stereotype.Component;

/**
 * @author Ivan Velichko
 * @date 03.11.2021 18:45
 */
@Component
public class OrderLinkBuilder extends AbstractLinkBuilder<OrderDto> {
    @Override
    public void addLinks(OrderDto orderDto) {
        addIdLinks(OrderController.class, orderDto, orderDto.getId(), SELF, UPDATE, DELETE);
    }
}
