package com.epam.esm.service;

import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author Ivan Velichko
 * @date 30.10.2021 13:51
 */
public interface UserService {
    PageDto<UserDto> findAll(Map<String, String> pageParams);
}
