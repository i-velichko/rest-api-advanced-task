package com.epam.esm.entity;

import lombok.*;

/**
 * @author Ivan Velichko
 * @date 01.11.2021 13:41
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Pagination {
    private int pageNumber;
    private int pageSize;
}
