package com.epam.esm.dto;

import lombok.*;

import java.util.List;

/**
 * @author Ivan Velichko
 * @date 01.11.2021 16:01
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PageDto <T> {
    private List<T> pagePositions;
    private long totalNumberPositions;
}
