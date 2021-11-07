package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.CertificateSearchParamsDto;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.CertificateSearchParams;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.IncorrectParamValueException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.handler.ParamsHandler;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.PaginationMapper;
import com.epam.esm.mapper.SearchParamMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.service.GiftCertificateService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Stream;

/**
 * @author Ivan Velichko
 * @date 28.10.2021 11:33
 */

@Service
@Transactional
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;
    private final TagDao tagDao;
    private final GiftCertificateMapper certificateMapper;
    private final TagMapper tagMapper;
    private final PaginationMapper paginationMapper;
    private final SearchParamMapper searchParamMapper;
    private final ParamsHandler paramsHandler;

    @Autowired
    public GiftCertificateServiceImpl(
            GiftCertificateDao giftCertificateDao,
            TagDao tagDao,
            GiftCertificateMapper certificateMapper,
            TagMapper tagMapper,
            PaginationMapper paginationMapper,
            SearchParamMapper searchParamMapper,
            ParamsHandler paramsHandler) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.certificateMapper = certificateMapper;
        this.tagMapper = tagMapper;
        this.paginationMapper = paginationMapper;
        this.searchParamMapper = searchParamMapper;
        this.paramsHandler = paramsHandler;
    }

    @Override
    public List<GiftCertificateDto> findAllBy(Map<String, String> params) {
        PaginationDto paginationDto = paramsHandler.getPaginationDto(params);
        CertificateSearchParamsDto searchParamsDto = paramsHandler.getGiftCertificatesSearchParamsDto(params);
        Pagination pagination = paginationMapper.paginationDtoToPagination(paginationDto);
        CertificateSearchParams searchParams = searchParamMapper.searchParamsDtoToSearchParams(searchParamsDto);
        List<GiftCertificate> certificates;
        if (ObjectUtils.allNotNull(
                searchParams.getPartNameOrDescription(),
                searchParams.getSortingOrderByDate(),
                searchParams.getSortingOrderByName(),
                searchParams.getTagNames())) {
            certificates = giftCertificateDao.findAllBy(pagination, searchParams);
        } else {
            certificates = giftCertificateDao.findAll(pagination);
        }
        return certificateMapper.certificatesToCertificateDtoList(certificates);
    }

    @Override
    public List<GiftCertificateDto> findAllBy(long orderId) {
        return certificateMapper.certificatesToCertificateDtoList(giftCertificateDao.findAllBy(orderId));
    }

    @Override
    public GiftCertificateDto findBy(long id) {
        return giftCertificateDao.findBy(id)
                .map(certificateMapper::certificateToCertificateDto)
                .orElseThrow(NoSuchEntityException::new);
    }

    @Override
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        GiftCertificate creatableCertificate = certificateMapper.certificateDtoToCertificate(giftCertificateDto);
        creatableCertificate.setTags(checkTagsToUpdate(giftCertificateDto.getTags()));
        return certificateMapper.certificateToCertificateDto(giftCertificateDao.create(creatableCertificate));
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto) {
        GiftCertificate giftCertificate = giftCertificateDao.findBy(giftCertificateDto.getId())
                .orElseThrow(() -> new NoSuchEntityException("error_message.certificate_not_found"));

        Set<Tag> tags = new HashSet<>();
        if (!CollectionUtils.isEmpty(giftCertificateDto.getTags())) {
            tags = checkTagsToUpdate(giftCertificateDto.getTags());
        }
        if (isAllFieldsNull(giftCertificateDto) && tags.isEmpty()) {
            throw new IncorrectParamValueException("certificate.update.no_data_for_update");
        }

        GiftCertificate updatableCertificate = createUpdatableCertificate(giftCertificate, certificateMapper.certificateDtoToCertificate(giftCertificateDto));
        updatableCertificate.getTags().addAll(tags);
        return certificateMapper.certificateToCertificateDto(giftCertificateDao.update(updatableCertificate));
    }

    private boolean isAllFieldsNull(GiftCertificateDto giftCertificateDto) {
        return Stream.of(giftCertificateDto.getName(), giftCertificateDto.getDescription(),
                giftCertificateDto.getDuration(), giftCertificateDto.getPrice()).allMatch(Objects::isNull);
    }

    private Set<Tag> checkTagsToUpdate(Set<TagDto> certificateTags) {
        Set<Tag> newCertificateTags = new HashSet<>();
        certificateTags.forEach(t -> {
            if (t.getId() != 0) {
                Tag tag = tagDao.findBy(t.getId())
                        .orElseThrow(() -> new NoSuchEntityException("giftcertificate.tag.not_present"));
                if (Objects.nonNull(t.getName()) && !t.getName().equalsIgnoreCase(tag.getName())) {
                    throw new IncorrectParamValueException("tag.name.not_correct_id_name_pair", t.getName());
                }
                newCertificateTags.add(tag);
            }
            if (Strings.isNotEmpty(t.getName()) && t.getId() == 0) {
                newCertificateTags.add(tagDao.findOrCreateTag(tagMapper.tagDtoToTag(t)));
            }
        });
        return newCertificateTags;
    }

    private GiftCertificate createUpdatableCertificate(GiftCertificate updatableCertificate, GiftCertificate giftCertificate) {
        if (Strings.isNotEmpty(giftCertificate.getName())) {
            updatableCertificate.setName(giftCertificate.getName());
        }

        if (Strings.isNotEmpty(giftCertificate.getDescription())) {
            updatableCertificate.setDescription(giftCertificate.getDescription());
        }

        if (Objects.nonNull(giftCertificate.getPrice())) {
            updatableCertificate.setPrice(giftCertificate.getPrice());
        }

        if (giftCertificate.getDuration() != 0) {
            updatableCertificate.setDuration(giftCertificate.getDuration());
        }
        return updatableCertificate;
    }
}
