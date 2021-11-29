package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Ivan Velichko
 * @date 30.10.2021 13:54
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id"})
public class OrderDto extends RepresentationModel<OrderDto> {

    @Positive
    private long id;

    public interface OnCreate {
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime orderDate;

    @Null
    private BigDecimal cost;

    @NotNull
    @Positive
    private long userId;

    @NotEmpty
    @NotBlank
    @Size(min = 1, max = 45)
    private String userNickname;

    private Set<Long> certificateIds;

}
