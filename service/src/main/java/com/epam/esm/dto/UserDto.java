package com.epam.esm.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

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
    private long id;
    private String nickname;
}
