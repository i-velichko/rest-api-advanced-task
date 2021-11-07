package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.linkbuilder.LinkBuilder;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Ivan Velichko
 * @date 31.10.2021 12:36
 */

@RestController
@RequestMapping("/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final LinkBuilder<OrderDto> linkBuilder;

    @Autowired
    public OrderController(OrderService orderService, LinkBuilder<OrderDto> linkBuilder) {
        this.orderService = orderService;
        this.linkBuilder = linkBuilder;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> findAll(@RequestParam Map<String, String> pageParams) {
        List<OrderDto> orderDtoList = orderService.findAll(pageParams);
        orderDtoList.forEach(linkBuilder::addLinks);
        return orderDtoList;
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> findAllBy(@PathVariable long userId) {
        List<OrderDto> orderDtoList = orderService.findAllBy(userId);
        orderDtoList.forEach(linkBuilder::addLinks);
        orderDtoList.forEach(orderDto -> orderDto.add(linkTo(methodOn(GiftCertificateController.class)
                .findAllBy(orderDto.getId()))
                .withRel("certificates"))); //todo paramName
        return orderDtoList;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto create(@RequestBody OrderDto orderDto) {
        OrderDto creatableOrder = orderService.create(orderDto);
        linkBuilder.addLinks(creatableOrder);
        return creatableOrder;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto findBy(@PathVariable long id) {
        OrderDto orderDto = orderService.findBy(id);
        linkBuilder.addLinks(orderDto);
        return orderDto;
    }
}
