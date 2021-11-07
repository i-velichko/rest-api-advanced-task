package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateEntityException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.handler.ParamsHandler;
import com.epam.esm.mapper.PaginationMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Ivan Velichko
 * @date 25.10.2021 23:29
 */

@Service
@Transactional
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
    public List<TagDto> findAll(Map<String, String> pageParams) {
        PaginationDto paginationDto = paramsHandler.getPaginationDto(pageParams);
        Pagination pagination = paginationMapper.paginationDtoToPagination(paginationDto);
        return tagMapper.tagsToTagDtoList(tagDao.findAll(pagination));
    }

    @Override
    public TagDto findBy(Long id) {
        return tagMapper.tagToTagDto(tagDao.findBy(id).orElseThrow(NoSuchEntityException::new));
    }

    @Override
    public TagDto findBy(String name) {
        return tagMapper.tagToTagDto(tagDao.findBy(name).orElseThrow(NoSuchEntityException::new));
    }

    @Override
    public TagDto create(TagDto tagDto) {
        if (tagDao.findBy(tagDto.getName()).isPresent()) {
            throw new DuplicateEntityException("error_message.exist");
        }
        Tag tag = tagDao.create(tagMapper.tagDtoToTag(tagDto));
        return tagMapper.tagToTagDto(tag);
    }

    @Override
    public void delete(Long id) {
        if (tagDao.findBy(id).isEmpty()) {
            throw new NoSuchEntityException();
        }
        tagDao.delete(id);
    }
}
