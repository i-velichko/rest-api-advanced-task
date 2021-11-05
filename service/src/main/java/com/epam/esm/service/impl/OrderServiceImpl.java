package com.epam.esm.service.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Pagination;
import com.epam.esm.handler.ParamsHandler;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.mapper.PaginationMapper;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Ivan Velichko
 * @date 31.10.2021 12:41
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final OrderMapper orderMapper;
    private final PaginationMapper paginationMapper;
    private final ParamsHandler paramsHandler;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, OrderMapper orderMapper, PaginationMapper paginationMapper, ParamsHandler paramsHandler) {
        this.orderDao = orderDao;
        this.orderMapper = orderMapper;
        this.paginationMapper = paginationMapper;
        this.paramsHandler = paramsHandler;
    }

    @Override
    public List<OrderDto> findAll(Map<String, String> pageParams) {
        Pagination pagination = paginationMapper.paginationDtoToPagination(paramsHandler.getPaginationDto(pageParams));
        return orderMapper.ordersToOrderDtoList(orderDao.findAll(pagination));
    }

    @Override
    public List<OrderDto> findAllBy(long userId) {
        return orderMapper.ordersToOrderDtoList(orderDao.findAllBy(userId));
    }
}
