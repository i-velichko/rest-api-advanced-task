package com.epam.esm.controller;

import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.handler.ParamsHandler;
import com.epam.esm.linkbuilder.LinkBuilder;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Ivan Velichko
 * @date 25.10.2021 23:34
 */

@RestController
@RequestMapping("/v1/tags")
public class TagController {
    private final TagService tagService;
    private final LinkBuilder<TagDto> linkBuilder;

    @Autowired
    public TagController(TagService tagService, LinkBuilder<TagDto> linkBuilder) {
        this.tagService = tagService;
        this.linkBuilder = linkBuilder;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageDto<TagDto> findAll(@RequestParam Map<String, String> pageParams) {
        PageDto<TagDto> pageDto = tagService.findAll(pageParams);
        pageDto.getPagePositions().forEach(linkBuilder::addLinks);
        return pageDto;
    }
}
