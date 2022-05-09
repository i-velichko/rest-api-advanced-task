package com.epam.esm.mapper;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * @author Ivan Velichko
 * @date 30.10.2021 14:18
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserDto userToDtoUser(User user);

    List<UserDto> usersToUserDtoList(List<User> users);

    @InheritInverseConfiguration
    User userDtoToUser(UserDto userDto);
}
