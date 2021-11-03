package com.epam.esm.service;

import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Map;

/**
 * @author Ivan Velichko
 * @date 25.10.2021 23:28
 */
public interface TagService {
    PageDto<TagDto> findAll(Map<String, String> pageParams);
}
