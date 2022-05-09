package com.epam.esm.linkbuilder.impl;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;
import org.springframework.stereotype.Component;

/**
 * @author Ivan Velichko
 * @date 03.11.2021 12:26
 */
@Component
public class TagLinkBuilder extends AbstractLinkBuilder<TagDto> {

    @Override
    public void addLinks(TagDto tagDto) {
        addIdLinks(TagController.class, tagDto, tagDto.getId(), SELF, DELETE);
    }
}
