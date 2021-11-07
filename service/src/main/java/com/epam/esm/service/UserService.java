package com.epam.esm.service;

import com.epam.esm.dto.UserDto;

import java.util.List;
import java.util.Map;

/**
 * @author Ivan Velichko
 * @date 30.10.2021 13:51
 */
public interface UserService {
    List<UserDto> findAll(Map<String, String> pageParams);

    UserDto findBy(Long id);

    UserDto create(UserDto userDto);

    UserDto update(UserDto userDto);
}
