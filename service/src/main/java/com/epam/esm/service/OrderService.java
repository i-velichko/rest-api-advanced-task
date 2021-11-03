package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.dto.TagDto;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author Ivan Velichko
 * @date 31.10.2021 12:40
 */
public interface OrderService {
    PageDto<OrderDto> findAll(Map<String, String> pageParams);
    List<OrderDto> findAllBy(long userId);
}
