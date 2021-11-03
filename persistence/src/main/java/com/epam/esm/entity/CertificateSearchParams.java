package com.epam.esm.entity;

import lombok.*;

import java.util.List;

/**
 * @author Ivan Velichko
 * @date 01.11.2021 13:42
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CertificateSearchParams {
    private List<String> tagNames;
    private String partNameOrDescription;
    private SortingOrder sortingOrderByDate;
    private SortingOrder sortingOrderByName;
}
