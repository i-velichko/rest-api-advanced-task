package com.epam.esm.mapper;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Ivan Velichko
 * @date 30.10.2021 14:13
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.nickname", target = "userNickname")
    default OrderDto orderToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setCost(order.getCost());
        orderDto.setUserId(order.getUser().getId());
        orderDto.setUserNickname(order.getUser().getNickname());
        Set<Long> set = order.getCertificates().stream().mapToLong(GiftCertificate::getId).boxed().collect(Collectors.toSet());
        orderDto.setCertificateIds(set
        );
        return orderDto;
    }

    List<OrderDto> ordersToOrderDtoList(List<Order> orders);
}
