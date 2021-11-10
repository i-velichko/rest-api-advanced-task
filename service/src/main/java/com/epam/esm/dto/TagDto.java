package com.epam.esm.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import static com.epam.esm.exception.CustomErrorMessageCode.TAG_NAME_INCORRECT;

/**
 * @author Ivan Velichko
 * @date 26.10.2021 12:39
 */


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id"})
public class TagDto extends RepresentationModel<TagDto> {

    private long id;

    @NotEmpty()
    @Size(min = 1, max = 45)
    private String name;
}
