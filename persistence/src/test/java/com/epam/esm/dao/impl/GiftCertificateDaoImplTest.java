package com.epam.esm.dao.impl;

import com.epam.esm.entity.CertificateSearchParams;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Pagination;
import config.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfig.class)
@Transactional
class GiftCertificateDaoImplTest {
    private static final long CERTIFICATE_ID = 1L;
    private static final long ORDER_ID = 1L;
    private static final String NEW_NAME = "newName";
    private static final String UPDATE_NAME = "update name";
    private static final String UPDATE_DESCRIPTION = "update description";
    private static final BigDecimal UPDATE_PRICE = BigDecimal.valueOf(30);
    private static final String NEW_DESCRIPTION = "newDescription";
    private static final BigDecimal NEW_PRICE = BigDecimal.valueOf(22);
    private static final int NEW_DURATION = 5;
    private static final int EXPECTED_CERTIFICATES_LIST_SIZE_3 = 3;
    private static final int EXPECTED_CERTIFICATES_LIST_SIZE_2 = 2;
    private static final int EXPECTED_CERTIFICATES_LIST_SIZE_1 = 1;
    private static final String CERTIFICATE1 = "certificate 1";
    private static final String WORK = "work";
    private static final String EPAM = "epam";
    private static final String DESCRIPTION_1 = "description 1";

    private final GiftCertificateDaoImpl toTest;
    private GiftCertificate giftCertificate;
    private Pagination pagination;
    private CertificateSearchParams searchParams;
    private List<String> tagNames;

    @Autowired
    public GiftCertificateDaoImplTest(GiftCertificateDaoImpl certificateDao) {
        this.toTest = certificateDao;
    }

    @BeforeEach
    void setUp() {
        pagination = new Pagination();
        pagination.setPageSize(3);
        giftCertificate = new GiftCertificate();
        giftCertificate.setName(NEW_NAME);
        giftCertificate.setDescription(NEW_DESCRIPTION);
        giftCertificate.setPrice(NEW_PRICE);
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setDuration(NEW_DURATION);
        searchParams = new CertificateSearchParams();
        tagNames = List.of(WORK, EPAM);
    }

    @Test
    void findAll() {
        List<GiftCertificate> all = toTest.findAll(pagination);
        int actualSize = all.size();
        assertEquals(EXPECTED_CERTIFICATES_LIST_SIZE_3, actualSize);
    }

    @Test
    void findAllByPartNamePositive() {
        searchParams.setPartNameOrDescription(CERTIFICATE1);
        int actualSize = toTest.findAllBy(pagination, searchParams).size();
        assertEquals(EXPECTED_CERTIFICATES_LIST_SIZE_1, actualSize);
    }

    @Test
    void findAllByTwoTagsPositive() {
        searchParams.setTagNames(tagNames);
        int actualSize = toTest.findAllBy(pagination, searchParams).size();
        assertEquals(EXPECTED_CERTIFICATES_LIST_SIZE_1, actualSize);
    }

    @Test
    void testFindAllByOrderIdPositive() {
        int actualSize = toTest.findAllBy(ORDER_ID).size();
        assertEquals(EXPECTED_CERTIFICATES_LIST_SIZE_2, actualSize);
    }

    @Test
    @Order(1)
    void createPositive() {
        GiftCertificate actual = toTest.create(giftCertificate);
        assertEquals(actual.getName(), NEW_NAME);
        assertEquals(actual.getDescription(), NEW_DESCRIPTION);
        assertEquals(actual.getPrice(), NEW_PRICE);
        assertEquals(actual.getDuration(), NEW_DURATION);
    }

    @Test
    @Order(2)
    void updatePositive() {
        giftCertificate.setName(UPDATE_NAME);
        giftCertificate.setDescription(UPDATE_DESCRIPTION);
        giftCertificate.setPrice(UPDATE_PRICE);
        GiftCertificate updatableCertificate = toTest.update(giftCertificate);
        assertEquals(updatableCertificate.getName(), UPDATE_NAME);
        assertEquals(updatableCertificate.getDescription(), UPDATE_DESCRIPTION);
        assertEquals(updatableCertificate.getPrice(), UPDATE_PRICE);
    }

    @Test
    void findByCertificateIdPositive() {
        GiftCertificate actual = toTest.findBy(CERTIFICATE_ID).stream().findFirst().orElse(new GiftCertificate());
        assertEquals(actual.getName(), CERTIFICATE1);
        assertEquals(actual.getDescription(), DESCRIPTION_1);
    }

}