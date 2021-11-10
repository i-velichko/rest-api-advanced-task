package com.epam.esm.handler;

import com.epam.esm.dto.CertificateSearchParamsDto;
import com.epam.esm.dto.PaginationDto;
import com.epam.esm.entity.SortingOrder;
import com.epam.esm.exception.ErrorDetails;
import com.epam.esm.exception.IncorrectParamValueException;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.epam.esm.exception.CustomErrorMessageCode.*;

/**
 * @author Ivan Velichko
 * @date 02.11.2021 16:47
 */
@Component
@PropertySource("classpath:error_code.properties")
public class ParamsHandler {
    @Value("01")
    private int PAGE_INVALID_NUMBER;
    @Value("02")
    private int PAGE_INVALID_SIZE;
    @Value("03")
    private int INVALID_SORTING_ORDER_BY_DATE;
    @Value("04")
    private int INVALID_SORTING_ORDER_BY_NAME;
    private static final String PAGE_NUMBER = "pageNumber";
    private static final String DEFAULT_PAGE_NUMBER = "1";
    private static final String PAGE_SIZE = "pageSize";
    private static final String DEFAULT_PAGE_SIZE = "5";
    private static final String NUMBER_AND_SIZE_PATTERN = "^[1-9][0-9]{0,8}$";
    private static final String TAG_NAMES = "tagNames";
    private static final String SEPARATOR_TAGS = ",";
    private static final String PART_NAME_OR_DESCRIPTION = "partNameOrDescription";
    private static final String SORTING_ORDER_BY_DATE = "sortingOrderByDate";
    private static final String SORTING_ORDER_BY_NAME = "sortingOrderByName";

    public PaginationDto getPaginationDto(Map<String, String> pageParams) {
        String pageNumber = StringUtils.isBlank(pageParams.get(PAGE_NUMBER)) ? DEFAULT_PAGE_NUMBER : pageParams.get(PAGE_NUMBER);
        String pageSize = StringUtils.isBlank(pageParams.get(PAGE_SIZE)) ? DEFAULT_PAGE_SIZE : pageParams.get(PAGE_SIZE);
        checkPageParamsValue(pageNumber, pageSize);
        int offset = ((Integer.parseInt(pageNumber) - 1)) * Integer.parseInt(pageSize);
        int limit = Integer.parseInt(pageSize);
        return new PaginationDto(offset, limit);
    }

    public CertificateSearchParamsDto getGiftCertificatesSearchParamsDto(Map<String, String> params) {
        CertificateSearchParamsDto searchParamsDto = new CertificateSearchParamsDto();
        String tagNames = params.get(TAG_NAMES);
        if (StringUtils.isNotBlank(tagNames)) {
            List<String> tagNamesList = Arrays.asList(tagNames.split(SEPARATOR_TAGS));
            searchParamsDto.setTagNames(tagNamesList.stream()
                    .map(String::strip)
                    .collect(Collectors.toList()));
        }
        searchParamsDto.setPartNameOrDescription(params.get(PART_NAME_OR_DESCRIPTION));
        setSortingOrderParamsValue(params, searchParamsDto);
        return searchParamsDto;
    }

    private void setSortingOrderParamsValue(Map<String, String> params, CertificateSearchParamsDto searchParamsDto) {
        String sortingOrderByDate = params.get(SORTING_ORDER_BY_DATE);
        String sortingOrderByName = params.get(SORTING_ORDER_BY_NAME);
        List<ErrorDetails> errors = new ArrayList<>();
        if (StringUtils.isNotBlank(sortingOrderByDate)) {
            if (isValidSortingOrder(sortingOrderByDate)) {
                searchParamsDto.setSortingOrderByDate(SortingOrder.valueOf(sortingOrderByDate.toUpperCase()));
            } else {
                ErrorDetails errorDetails = new ErrorDetails(INCORRECT_SORTING_ORDER_BY_DATE, sortingOrderByDate,
                        INVALID_SORTING_ORDER_BY_DATE);
                errors.add(errorDetails);
            }
        }
        if (StringUtils.isNotBlank(sortingOrderByName)) {
            if (isValidSortingOrder(sortingOrderByName)) {
                searchParamsDto.setSortingOrderByName(SortingOrder.valueOf(sortingOrderByName.toUpperCase()));
            } else {
                ErrorDetails errorDetails = new ErrorDetails(INCORRECT_SORTING_ORDER_BY_NAME, sortingOrderByName,
                        INVALID_SORTING_ORDER_BY_NAME);
                errors.add(errorDetails);
            }
        }
        if (!errors.isEmpty()) {
            throw new IncorrectParamValueException(INVALID_SORTING_ORDERS_PARAM, errors);
        }
    }

    private void checkPageParamsValue(String number, String size) {
        List<ErrorDetails> errors = new ArrayList<>();
        if (!number.matches(NUMBER_AND_SIZE_PATTERN)) {
            ErrorDetails errorDetails = new ErrorDetails(INCORRECT_PAGE_NUMBER, number,
                    PAGE_INVALID_NUMBER);
            errors.add(errorDetails);
        }
        if (!size.matches(NUMBER_AND_SIZE_PATTERN)) {
            ErrorDetails errorDetails = new ErrorDetails(INCORRECT_PAGE_SIZE, size,
                    PAGE_INVALID_SIZE);
            errors.add(errorDetails);
        }
        if (!errors.isEmpty()) {
            throw new IncorrectParamValueException(INVALID_PAGE_PARAMS, errors);
        }
    }

    private boolean isValidSortingOrder(String sortingOrderParamValue) {
        return StringUtils.isNotBlank(sortingOrderParamValue)
                && EnumUtils.isValidEnum(SortingOrder.class, sortingOrderParamValue.toUpperCase());
    }
}
