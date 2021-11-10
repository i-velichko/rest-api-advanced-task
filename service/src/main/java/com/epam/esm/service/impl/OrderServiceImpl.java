package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.handler.ParamsHandler;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.mapper.PaginationMapper;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.exception.CustomErrorMessageCode.USER_NOT_FOUND;

/**
 * @author Ivan Velichko
 * @date 31.10.2021 12:41
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final GiftCertificateDao giftCertificateDao;
    private final OrderMapper orderMapper;
    private final PaginationMapper paginationMapper;
    private final ParamsHandler paramsHandler;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, UserDao userDao, GiftCertificateDao giftCertificateDao,
                            OrderMapper orderMapper, PaginationMapper paginationMapper, ParamsHandler paramsHandler) {
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.giftCertificateDao = giftCertificateDao;
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

    @Override
    public OrderDto findBy(Long id) {
        return orderDao.findBy(id)
                .map(orderMapper::orderToOrderDto)
                .orElseThrow(NoSuchEntityException::new);
    }

    @Override
    public OrderDto create(OrderDto orderDto) {
        Optional<User> optionalUser = userDao.findBy(orderDto.getUserId());
        if (optionalUser.isEmpty()) {
            throw new NoSuchEntityException(USER_NOT_FOUND);
        } else {
            Order order = orderMapper.orderDtoToOrder(orderDto);
            order.setUser(optionalUser.get());
            OrderDto creatableOrder = new OrderDto();
            Set<Long> certificateIds = orderDto.getCertificateIds();
            if (certificateIds != null) {
                List<GiftCertificate> giftCertificates = certificateIds.stream()
                        .map(giftCertificateDao::findBy)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());
                BigDecimal total = giftCertificates.stream()
                        .map(GiftCertificate::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                order.setCertificates(giftCertificates);
                order.setCost(total);
                creatableOrder = orderMapper.orderToOrderDto(orderDao.create(order));
                giftCertificates.forEach(giftCertificate -> {
                    giftCertificate.setOrderId(order.getId());
                    giftCertificateDao.update(giftCertificate);
                });
            }
            return creatableOrder;
        }

    }
}
