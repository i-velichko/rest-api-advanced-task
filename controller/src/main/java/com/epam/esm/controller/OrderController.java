package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.linkbuilder.LinkBuilder;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public PageDto<OrderDto> findAll(@RequestParam Map<String, String> pageParams) {
        PageDto<OrderDto> pageDto = orderService.findAll(pageParams);
        pageDto.getPagePositions().forEach(linkBuilder::addLinks);
        return pageDto;
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> findAllBy(@PathVariable long userId) {
        return orderService.findAllBy(userId);
    }
}
