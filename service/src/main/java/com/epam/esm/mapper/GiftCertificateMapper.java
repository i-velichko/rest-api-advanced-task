package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

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

}
