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
    private String LOCALE_US;
    @Value("${locale.ru}")
    private String LOCALE_RU;
    @Value("${language.en}")
    private String LANGUAGE_EN;
    @Value("${country.us}")
    private String COUNTRY_US;

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
        List<String> AVAILABLE_LOCALES = Arrays.asList(LOCALE_US, LOCALE_RU);
        Locale DEFAULT_LOCALE = new Locale(LANGUAGE_EN, COUNTRY_US);
        if (!AVAILABLE_LOCALES.contains(locale.toString())) {
            locale = DEFAULT_LOCALE;
        }
        return resourceBundleMessageSource.getMessage(key, null, locale);
    }
}
