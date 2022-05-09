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
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.handler.ParamsHandler;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.PaginationMapper;
import com.epam.esm.mapper.SearchParamMapper;
import com.epam.esm.mapper.TagMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.*;

import static java.math.BigDecimal.valueOf;
import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class GiftCertificateServiceImplTest {
    private static final int EXPECTED_CERTIFICATES_SIZE = 2;
    private static final long ID_ONE = 1;
    private static final long ID_TWO = 2;
    GiftCertificate certificate1;
    GiftCertificate certificate2;
    GiftCertificateDto certificateDto1;
    GiftCertificateDto certificateDto2;
    private CertificateSearchParamsDto searchParamsDto;
    private CertificateSearchParams certificateSearchParams;
    private PaginationDto paginationDto;
    private Pagination pagination;
    private Map<String, String> searchParams;

    private GiftCertificateServiceImpl toTest;
    @Mock
    private GiftCertificateDao giftCertificateDao;
    @Mock
    private TagDao tagDao;
    @Mock
    private GiftCertificateMapper certificateMapper;
    @Mock
    private TagMapper tagMapper;
    @Mock
    private PaginationMapper paginationMapper;
    @Mock
    private SearchParamMapper searchParamMapper;
    @Mock
    private ParamsHandler paramsHandler;


    @BeforeEach
    void setUp() {
        openMocks(this);

        Set<Tag> tags = Set.of(new Tag(3, "tag3"), new Tag(4, "tag4"));
        Set<TagDto> tagDtoSet = Set.of(new TagDto(3, "tag1"), new TagDto(4, "tag2"));
        certificate1 = new GiftCertificate(1, "certificate1", "description1", valueOf(10), now(), now(), 10, tags, 1L);
        certificate2 = new GiftCertificate(2, "certificate2", "description2", valueOf(10), now(), now(), 10, tags, 1L);
        List<GiftCertificate> certificates = List.of(certificate1, certificate2);
        certificateDto1 = new GiftCertificateDto(1, "certificate1", "description1", valueOf(10), now(), now(), 10, tagDtoSet);
        certificateDto2 = new GiftCertificateDto(2, "certificate2", "description2", valueOf(10), now(), now(), 10, tagDtoSet);
        List<GiftCertificateDto> giftCertificateDtoList = List.of(certificateDto1, certificateDto2);
        searchParamsDto = new CertificateSearchParamsDto();
        searchParamsDto.setTagNames(List.of("tag1", "tag2"));
        searchParamsDto.setPartNameOrDescription("certificate");
        certificateSearchParams = new CertificateSearchParams();
        certificateSearchParams.setTagNames(List.of("tag1", "tag2"));
        certificateSearchParams.setPartNameOrDescription("certificate1");
        paginationDto = new PaginationDto();
        paginationDto.setPageNumber(0);
        paginationDto.setPageSize(3);
        pagination = new Pagination();
        pagination.setPageNumber(0);
        pagination.setPageSize(2);


        searchParams = new HashMap<>();
        searchParams.put("tagNames", "tag1, tag2");
        searchParams.put("partNameOrDescription", "certificate1");

        when(giftCertificateDao.findBy(1)).thenReturn(Optional.of(certificate1));
        when(giftCertificateDao.findAllBy(1)).thenReturn(certificates);
        when(giftCertificateDao.findAllBy(any(Pagination.class), any(CertificateSearchParams.class))).thenReturn(certificates);
        when(certificateMapper.certificatesToCertificateDtoList(eq(certificates))).thenReturn(giftCertificateDtoList);
        when(certificateMapper.certificateToCertificateDto(eq(certificate1))).thenReturn(certificateDto1);
        when(paramsHandler.getGiftCertificatesSearchParamsDto(anyMap())).thenReturn(searchParamsDto);
        when(paramsHandler.getPaginationDto(anyMap())).thenReturn(paginationDto);
        when(paginationMapper.paginationDtoToPagination((any(PaginationDto.class)))).thenReturn(pagination);
        when(searchParamMapper.searchParamsDtoToSearchParams(any(CertificateSearchParamsDto.class))).thenReturn(certificateSearchParams);

        toTest = new GiftCertificateServiceImpl(
                giftCertificateDao, tagDao, certificateMapper, tagMapper,
                paginationMapper, searchParamMapper, paramsHandler);
    }

    @Test
    void findByIdPositive() {
        GiftCertificateDto certificateDto = toTest.findBy(ID_ONE);
        assertEquals(certificateDto1, certificateDto);
    }

    @Test
    void findAllByNegativeWhenCertificateNotExist() {
        assertThrows(NoSuchEntityException.class, () -> toTest.findBy(3));
    }

    @Test
    void testFindAllByOrderIdPositive() {
        int actualSize = toTest.findAllBy(1).size();
        int expectedSize = 2;
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void testFindAllByNegativeWhenOrdersNotExist() {
        assertThrows(NoSuchEntityException.class, () -> toTest.findBy(2));
    }

    @Test
    void findAllBySearchParamsPositive() {
        int actualSize = toTest.findAllBy(searchParams).size();
        assertEquals(2, actualSize);
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }
}