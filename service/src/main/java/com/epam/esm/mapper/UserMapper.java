package com.epam.esm.mapper;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Ivan Velichko
 * @date 30.10.2021 14:18
 */
@Mapper(componentModel = "spring", uses = {Order.class, GiftCertificate.class})
public interface UserMapper {

    UserDto userToDtoUser(User user);

    List<UserDto> usersToUserDtoList(List<User> users);
}
