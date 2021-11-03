package com.epam.esm.mapper;

import com.epam.esm.dto.CertificateSearchParamsDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.entity.CertificateSearchParams;
import com.epam.esm.entity.Pagination;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * @author Ivan Velichko
 * @date 01.11.2021 16:09
 */
@Mapper(componentModel = "spring")
public interface SearchParamMapper {
    CertificateSearchParamsDto searchParamsToDto(CertificateSearchParams searchParams);

    @InheritInverseConfiguration
    CertificateSearchParams searchParamsDtoToSearchParams(CertificateSearchParamsDto searchParamsDto);
}
