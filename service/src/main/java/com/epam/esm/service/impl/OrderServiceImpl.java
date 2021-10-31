package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ivan Velichko
 * @date 31.10.2021 12:41
 */
@Service
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao;
    private OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, OrderMapper orderMapper) {
        this.orderDao = orderDao;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<OrderDto> findAll() {
        return orderMapper.ordersToOrderDtoList(orderDao.findAll());
    }

    @Override
    public List<OrderDto> findAllBy(long userId) {
        return orderMapper.ordersToOrderDtoList(orderDao.findAllBy(userId));
    }
}
