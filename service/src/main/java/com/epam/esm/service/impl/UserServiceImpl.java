package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.UserDto;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ivan Velichko
 * @date 30.10.2021 13:51
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserDao userDao) {
        this.userMapper = userMapper;
        this.userDao = userDao;
    }

    @Override
    public List<UserDto> findAll() {
        return userMapper.usersToUserDtoList(userDao.findAll());
    }
}
