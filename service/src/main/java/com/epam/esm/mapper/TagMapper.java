package com.epam.esm.mapper;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * @author Ivan Velichko
 * @date 26.10.2021 15:55
 */

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = GiftCertificate.class)
public interface TagMapper {

    TagDto modelToDto(Tag tag);

    List<TagDto> tagsToTagDtoList(List<Tag> tags);

    @InheritInverseConfiguration
    Tag tagDtoToTag(TagDto tagDto);

}
