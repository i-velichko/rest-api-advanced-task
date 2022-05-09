package com.epam.esm.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author Ivan Velichko
 * @date 15.10.2021 11:18
 */

@Component
@PropertySource("classpath:locale.properties")
public class I18nManager {
    @Value("${locale.us}")
    private String localeUS;
    @Value("${locale.ru}")
    private String localeRu;
    @Value("${language.en}")
    private String languageEn;
    @Value("${country.us}")
    private String countryUS;

    private final ResourceBundleMessageSource resourceBundleMessageSource;

    @Autowired
    public I18nManager(ResourceBundleMessageSource resourceBundleMessageSource) {
        this.resourceBundleMessageSource = resourceBundleMessageSource;
    }

    public List<String> getLocaleValidationErrorMessages(BindingResult bindingResult, Locale locale) {
        List<String> errors = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            List<FieldError> errorList = bindingResult.getFieldErrors();
            for (FieldError error : errorList) {
                String localeMsg = getMessage(error.getDefaultMessage(), locale);
                errors.add(localeMsg);
            }
        }
        return errors;
    }

    public String getMessage(String key, Locale locale) {
        List<String> availableLocales = Arrays.asList(localeUS, localeRu);
        Locale defaultLocale = new Locale(languageEn, countryUS);
        if (!availableLocales.contains(locale.toString())) {
            locale = defaultLocale;
        }
        return resourceBundleMessageSource.getMessage(key, null, locale);
    }
}
