package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.linkbuilder.LinkBuilder;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Ivan Velichko
 * @date 30.10.2021 14:42
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;
    private final LinkBuilder<UserDto> linkBuilder;

    @Autowired
    public UserController(UserService userService, LinkBuilder<UserDto> linkBuilder) {
        this.userService = userService;
        this.linkBuilder = linkBuilder;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> findAll(@RequestParam Map<String, String> pageParams) {
        List<UserDto> userDtoList = userService.findAll(pageParams);
        userDtoList.forEach(linkBuilder::addLinks);
        return userDtoList;
    }
}
