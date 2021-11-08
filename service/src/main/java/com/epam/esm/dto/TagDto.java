package com.epam.esm.dto;

import com.epam.esm.entity.GiftCertificate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

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

    @NotNull()
    @Positive
    private long id;

    @NotEmpty
    @Size(min = 1, max = 45)
    private String name;


}
