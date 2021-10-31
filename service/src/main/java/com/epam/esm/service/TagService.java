package com.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;

import java.util.List;

/**
 * @author Ivan Velichko
 * @date 25.10.2021 23:28
 */
public interface TagService {
    List<TagDto> findAll();
}
