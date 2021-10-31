package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ivan Velichko
 * @date 25.10.2021 23:29
 */

@Service
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final TagMapper tagMapper;


    @Autowired
    public TagServiceImpl(TagDao tagDao, TagMapper tagMapper) {
        this.tagDao = tagDao;
        this.tagMapper = tagMapper;
    }

    @Override
    public List<TagDto> findAll() {
        List<TagDto> tagDtoList = tagMapper.tagsToTagDtoList(tagDao.findAll());
        return tagDtoList;
    }
}
