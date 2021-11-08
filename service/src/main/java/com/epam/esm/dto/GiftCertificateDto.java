package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Ivan Velichko
 * @date 28.10.2021 11:15
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "tags"})
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {

    public interface OnCreate {
    }

    public interface OnUpdate {
    }

    @Null(groups = OnCreate.class)
    @NotNull(groups = OnUpdate.class)
    @Positive
    private long id;

    @NotEmpty(groups = OnCreate.class)
    @Size(min = 1, max = 30, groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @NotNull(groups = OnCreate.class)
    @Size(min = 1, max = 200, groups = {OnCreate.class, OnUpdate.class})
    private String description;

    @NotNull(groups = {OnCreate.class})
    @Digits(integer = 6, fraction = 2, groups = {OnCreate.class, OnUpdate.class})
    @DecimalMin(groups = {OnCreate.class, OnUpdate.class}, value = "0.10")
    private BigDecimal price;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime lastUpdateDate;

    @NotNull(groups = OnCreate.class)
    @Min(value = 1, groups = {OnCreate.class, OnUpdate.class})
    @Max(value = 1000, groups = {OnCreate.class, OnUpdate.class})
    private int duration;

    @Valid
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<TagDto> tags;
}
