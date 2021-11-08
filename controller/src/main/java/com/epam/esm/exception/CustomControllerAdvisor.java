package com.epam.esm.exception;

import com.epam.esm.i18n.I18nManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static com.epam.esm.exception.CustomErrorMessageCode.ENTITY_NOT_FOUND;
import static com.epam.esm.exception.CustomErrorMessageCode.TAG_CAN_NOT_BE_REMOVED;

/**
 * @author Ivan Velichko
 * @date 04.10.2021 10:37
 */

@ControllerAdvice
@PropertySource("classpath:error_code.properties")
public class CustomControllerAdvisor {
    private static final String UNDEFINED_DAO = "undefined dao";
    private final I18nManager i18nManager;

    @Value("${no.such.entity.code}")
    private int NO_SUCH_ENTITY_CODE;
    @Value("${empty.result.data.access.code}")
    private int EMPTY_RESULT_DATA_ACCESS_CODE;
    @Value("${data.integrity.violation.code}")
    private int DATA_INTEGRITY_VIOLATION_CODE;
    @Value("${duplicate.entity.code}")
    private int DUPLICATE_ENTITY_CODE;
    @Value("${method.argument.not.valid.code}")
    private int METHOD_ARGUMENT_NOT_VALID_CODE;
    @Value("${no.such.parameter.code}")
    private int NO_SUCH_PARAMETER_CODE;
    @Value("${convert.entity.error.code}")
    private int CONVERT_ENTITY_ERROR_CODE;

    @Autowired
    public CustomControllerAdvisor(I18nManager i18nManager) {
        this.i18nManager = i18nManager;
    }

    @ExceptionHandler(NoSuchEntityException.class)
    public ResponseEntity<CustomResponse> handleNoSuchEntityException(NoSuchEntityException e, Locale locale) {
        String localeMsg = i18nManager.getMessage(e.getMessage(), locale);
        CustomResponse response = new CustomResponse(localeMsg, NO_SUCH_ENTITY_CODE);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<CustomResponse> handleEmptyResultDataAccessException(EmptyResultDataAccessException e, Locale locale) {
        String msg = getEntityNameByMsg(e, ".", "Dao").toLowerCase(Locale.ROOT);
        String localeMsg = i18nManager.getMessage(ENTITY_NOT_FOUND + msg, locale);
        CustomResponse response = new CustomResponse(localeMsg, EMPTY_RESULT_DATA_ACCESS_CODE);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e, Locale locale) {
        String localeMsg = i18nManager.getMessage(TAG_CAN_NOT_BE_REMOVED, locale);
        CustomResponse response = new CustomResponse(localeMsg, DATA_INTEGRITY_VIOLATION_CODE);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<CustomResponse> handleDuplicateEntityException(DuplicateEntityException e, Locale locale) {
        String localeMsg = i18nManager.getMessage(e.getMessage(), locale);
        CustomResponse response = new CustomResponse(localeMsg, DUPLICATE_ENTITY_CODE);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomValidationResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, Locale locale) {
        BindingResult bindingResult = e.getBindingResult();
        List<String> errors = i18nManager.getLocaleValidationErrorMessages(bindingResult, locale);
        CustomValidationResponse response = new CustomValidationResponse(errors, METHOD_ARGUMENT_NOT_VALID_CODE);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchParameterException.class)
    public ResponseEntity<CustomResponse> handleNoSuchParameterException(NoSuchParameterException e, Locale locale) {
        String localeMsg = i18nManager.getMessage(e.getMessage(), locale);
        CustomResponse response = new CustomResponse(localeMsg, NO_SUCH_PARAMETER_CODE);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConvertEntityException.class)
    public ResponseEntity<CustomResponse> handleConvertEntityException(ConvertEntityException e, Locale locale) {
        String localeMsg = i18nManager.getMessage(e.getMessage(), locale);
        CustomResponse response = new CustomResponse(localeMsg, CONVERT_ENTITY_ERROR_CODE);
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private String getEntityNameByMsg(Exception e, String beforeName, String afterName) {
        String text = msgFromStack(e, afterName);
        return text.substring(text.lastIndexOf(beforeName) + 1, text.indexOf(afterName));
    }

    private String msgFromStack(Exception e, String afterName) {
        Optional<String> dao = Arrays.stream(e.getStackTrace())
                .map(StackTraceElement::getClassName)
                .filter(className -> className.contains(afterName))
                .findAny();
        return dao.orElse(UNDEFINED_DAO);
    }
}
