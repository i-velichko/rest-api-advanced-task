package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * @author Ivan Velichko
 * @date 28.10.2021 11:34
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = Tag.class)
public interface GiftCertificateMapper {
    GiftCertificateDto certificateToCertificateDto(GiftCertificate certificate);

    List<GiftCertificateDto> certificatesToCertificateDtoList(List<GiftCertificate> certificates);

    TagDto modelToDto(Tag tag);

    List<TagDto> tagsToTagDtoList(List<Tag> tags);
}
