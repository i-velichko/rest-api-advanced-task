package com.epam.esm.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

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

    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 45)
    private String name;
}
