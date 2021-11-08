package com.epam.esm.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.*;

/**
 * @author Ivan Velichko
 * @date 30.10.2021 13:52
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id"})
public class UserDto extends RepresentationModel<UserDto> {

    public interface OnCreate {
    }

    public interface OnUpdate {
    }

    @Null(groups = UserDto.OnCreate.class)
    @NotNull(groups = UserDto.OnUpdate.class)
    @Positive
    private long id;

    @NotEmpty(groups = {UserDto.OnCreate.class, UserDto.OnUpdate.class})
    @NotNull(groups = UserDto.OnCreate.class)
    @Size(min = 1, max = 45)
    private String nickname;
}
