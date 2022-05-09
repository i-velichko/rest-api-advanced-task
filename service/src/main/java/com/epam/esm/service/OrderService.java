package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;

import java.util.List;
import java.util.Map;

/**
 * @author Ivan Velichko
 * @date 31.10.2021 12:40
 */
public interface OrderService {
    List<OrderDto> findAll(Map<String, String> pageParams);

    List<OrderDto> findAllBy(long userId);

    OrderDto findBy(Long id);

    OrderDto create(OrderDto orderDto);
}
