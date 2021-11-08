package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.linkbuilder.LinkBuilder;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan Velichko
 * @date 28.10.2021 11:38
 */

@RestController
@Validated
@RequestMapping("/v1/certificates")
public class GiftCertificateController {
    private final GiftCertificateService giftCertificateService;
    private final LinkBuilder<GiftCertificateDto> linkBuilder;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService, LinkBuilder<GiftCertificateDto> linkBuilder) {
        this.giftCertificateService = giftCertificateService;
        this.linkBuilder = linkBuilder;
    }

    @PostMapping
    @Validated(GiftCertificateDto.OnCreate.class)
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto create(@Valid @RequestBody GiftCertificateDto certificateDto) {
        GiftCertificateDto giftCertificateDto = giftCertificateService.create(certificateDto);
        linkBuilder.addLinks(giftCertificateDto);
        return giftCertificateDto;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> findAllBy(@RequestParam Map<String, String> params) {
        List<GiftCertificateDto> giftCertificateDtoList = giftCertificateService.findAllBy(params);
        giftCertificateDtoList.forEach(linkBuilder::addLinks);
        return giftCertificateDtoList;
    }

    @GetMapping("/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public List<GiftCertificateDto> findAllBy(@PathVariable @Positive long orderId) {
        List<GiftCertificateDto> giftCertificateDtoList = giftCertificateService.findAllBy(orderId);
        giftCertificateDtoList.forEach(linkBuilder::addLinks);
        return giftCertificateDtoList;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto findBy(@PathVariable @Positive long id) {
        GiftCertificateDto giftCertificateDto = giftCertificateService.findBy(id);
        linkBuilder.addLinks(giftCertificateDto);
        return giftCertificateDto;
    }

    @PatchMapping("/{id}")
    @Validated(GiftCertificateDto.OnUpdate.class)
    @ResponseStatus(HttpStatus.OK)
    public GiftCertificateDto updateGiftCertificate(@PathVariable @Positive long id, @Valid @RequestBody GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.setId(id);
        GiftCertificateDto updatedGiftCertificateDto = giftCertificateService.update(giftCertificateDto);
        linkBuilder.addLinks(updatedGiftCertificateDto);
        return updatedGiftCertificateDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Positive long id) {
        giftCertificateService.delete(id);

    }
}
