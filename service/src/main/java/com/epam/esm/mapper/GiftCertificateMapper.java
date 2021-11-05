package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

/**
 * @author Ivan Velichko
 * @date 28.10.2021 11:34
 */
@Mapper(componentModel = "spring")
public interface GiftCertificateMapper {
    GiftCertificateDto certificateToCertificateDto(GiftCertificate certificate);

    List<GiftCertificateDto> certificatesToCertificateDtoList(List<GiftCertificate> certificates);

    @InheritInverseConfiguration
    GiftCertificate certificateDtoToCertificate(GiftCertificateDto certificateDto);

//    TagDto modelToDto(Tag tag);
//
//    Set<TagDto> tagsToTagDtoSet(Set<Tag> tags);
}
