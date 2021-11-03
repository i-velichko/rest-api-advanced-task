package com.epam.esm.mapper;

import com.epam.esm.dto.PaginationDto;
import com.epam.esm.entity.Pagination;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * @author Ivan Velichko
 * @date 01.11.2021 16:05
 */
@Mapper(componentModel = "spring")
public interface PaginationMapper {
    PaginationDto paginationToPaginationDto(Pagination pagination);

    @InheritInverseConfiguration
    Pagination paginationDtoToPagination(PaginationDto paginationDto);
}
