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

    @GetMapping(value = "/{id}")
    public UserDto findBy(@PathVariable Long id) {
        UserDto userDto = userService.findBy(id);
        linkBuilder.addLinks(userDto);
        return userDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserDto userDto) {
        UserDto uDto = userService.create(userDto);
        linkBuilder.addLinks(uDto);
        return uDto;
    }

    @PatchMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto update(@PathVariable Long id, @RequestBody UserDto userDto) {
        userDto.setId(id);
        return userService.update(userDto);
    }
}
