package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.PageDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.linkbuilder.LinkBuilder;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author Ivan Velichko
 * @date 28.10.2021 11:38
 */

@RestController
@RequestMapping("/v1/certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;
    private final LinkBuilder<GiftCertificateDto> linkBuilder;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, LinkBuilder<GiftCertificateDto> linkBuilder) {
        this.giftCertificateService = giftCertificateService;
        this.linkBuilder = linkBuilder;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PageDto<GiftCertificateDto> findAllBy(@RequestParam Map<String, String> params) {
        PageDto<GiftCertificateDto> pageDto = giftCertificateService.findAllBy(params);
        pageDto.getPagePositions().forEach(linkBuilder::addLinks);
        return pageDto;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto getGiftCertificateById(@PathVariable long id) {
        GiftCertificateDto giftCertificateDto = giftCertificateService.findBy(id);
        linkBuilder.addLinks(giftCertificateDto);
        return giftCertificateDto;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto updateGiftCertificate(@PathVariable long id, @RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setId(id);
        GiftCertificateDto updatedGiftCertificateDto = giftCertificateService.update(giftCertificateDto);
        linkBuilder.addLinks(updatedGiftCertificateDto);
        return updatedGiftCertificateDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        giftCertificateService.delete(id);

    }

}
