package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;

import java.util.List;

/**
 * @author Ivan Velichko
 * @date 30.10.2021 13:51
 */
public interface UserService {
    List<UserDto> findAll();
}
