package com.epam.esm.dto;

import com.epam.esm.entity.SortingOrder;
import lombok.*;

import java.util.List;

/**
 * @author Ivan Velichko
 * @date 01.11.2021 15:57
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CertificateSearchParamsDto {
    private List<String> tagNames;
    private String partNameOrDescription;
    private SortingOrder sortingOrderByDate;
    private SortingOrder sortingOrderByName;
}
