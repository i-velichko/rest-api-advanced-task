package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;

import java.util.List;

/**
 * @author Ivan Velichko
 * @date 31.10.2021 12:40
 */
public interface OrderService {
    List<OrderDto> findAll();
    List<OrderDto> findAllBy(long userId);
}
