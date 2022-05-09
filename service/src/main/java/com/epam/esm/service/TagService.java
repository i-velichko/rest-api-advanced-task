package com.epam.esm.service;

import com.epam.esm.dto.TagDto;

import java.util.List;
import java.util.Map;

/**
 * @author Ivan Velichko
 * @date 25.10.2021 23:28
 */
public interface TagService {
    List<TagDto> findAll(Map<String, String> pageParams);

    TagDto findBy(Long id);

    TagDto findBy(String name);

    TagDto create(TagDto tagDto);

    TagDto findMostUsersWidelyUsedTag(long userId);

    void delete(Long id);
}
