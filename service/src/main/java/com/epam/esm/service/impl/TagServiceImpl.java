package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Pagination;
import com.epam.esm.handler.ParamsHandler;
import com.epam.esm.mapper.PaginationMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Ivan Velichko
 * @date 25.10.2021 23:29
 */

@Service
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;
    private final TagMapper tagMapper;
    private final PaginationMapper paginationMapper;
    private final ParamsHandler paramsHandler;


    @Autowired
    public TagServiceImpl(TagDao tagDao, TagMapper tagMapper, PaginationMapper paginationMapper, ParamsHandler paramsHandler) {
        this.tagDao = tagDao;
        this.tagMapper = tagMapper;
        this.paginationMapper = paginationMapper;
        this.paramsHandler = paramsHandler;
    }

    @Override
    public PageDto<TagDto> findAll(Map<String, String> pageParams) {
        PaginationDto paginationDto = paramsHandler.getPaginationDto(pageParams);
        Pagination pagination = paginationMapper.paginationDtoToPagination(paginationDto);
        List<TagDto> tags = tagMapper.tagsToTagDtoList(tagDao.findAll(pagination));
        long countQuery = tagDao.countQuery();
        return new PageDto<>(tags,countQuery);
    }
}
