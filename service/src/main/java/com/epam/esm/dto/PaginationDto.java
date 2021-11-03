package com.epam.esm.dto;

import lombok.*;

/**
 * @author Ivan Velichko
 * @date 01.11.2021 15:54
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PaginationDto {
    private int offset;
    private int limit;
}
