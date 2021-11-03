package com.epam.esm.linkbuilder.impl;

import com.epam.esm.controller.UserController;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import org.springframework.stereotype.Component;

/**
 * @author Ivan Velichko
 * @date 03.11.2021 18:48
 */
@Component
public class UserLinkBuilder extends AbstractLinkBuilder<UserDto>{
    @Override
    public void addLinks(UserDto userDto) {
        addIdLinks(UserController.class, userDto, userDto.getId(), SELF, DELETE);
    }
}
