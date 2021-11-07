package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.linkbuilder.LinkBuilder;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<TagDto> findAll(@RequestParam Map<String, String> pageParams) {
        return tagService.findAll(pageParams).stream().peek(linkBuilder::addLinks).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TagDto findBy(@PathVariable Long id) {
        TagDto tagDto = tagService.findBy(id);
        linkBuilder.addLinks(tagDto);
        return tagDto;
    }

    @GetMapping("/name")
    public TagDto findBy(@RequestParam(value = "name") String name) {
        TagDto tagDto = tagService.findBy(name);
        linkBuilder.addLinks(tagDto);
        return tagDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto create(@RequestBody TagDto tagDto) {
        return tagService.create(tagDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        try {
            tagService.delete(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("entity.remove.tag"); //todo !!!
        }
    }
}
