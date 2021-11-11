package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.handler.ParamsHandler;
import com.epam.esm.mapper.PaginationMapper;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.epam.esm.exception.CustomErrorMessageCode.USER_NOT_FOUND;

/**
 * @author Ivan Velichko
 * @date 30.10.2021 13:51
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserDao userDao;
    private final ParamsHandler paramsHandler;
    private final PaginationMapper paginationMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserDao userDao, ParamsHandler paramsHandler, PaginationMapper paginationMapper) {
        this.userMapper = userMapper;
        this.userDao = userDao;
        this.paramsHandler = paramsHandler;
        this.paginationMapper = paginationMapper;
    }

    @Override
    public List<UserDto> findAll(Map<String, String> pageParams) {
        Pagination pagination = paginationMapper.paginationDtoToPagination(paramsHandler.getPaginationDto(pageParams));
        return userMapper.usersToUserDtoList(userDao.findAll(pagination));
    }

    @Override
    public UserDto findBy(Long id) {
        return userDao.findBy(id)
                .map(userMapper::userToDtoUser)
                .orElseThrow(() -> new NoSuchEntityException(USER_NOT_FOUND));
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = userDao.create(userMapper.userDtoToUser(userDto));
        return userMapper.userToDtoUser(user);
    }

    @Override
    public UserDto update(UserDto userDto) {
        if (!userDao.isPresent(userDto.getId())) {
            throw new NoSuchEntityException();
        }
        User updatedUser = userDao.update(userMapper.userDtoToUser(userDto));
        return userMapper.userToDtoUser(updatedUser);
    }
}
