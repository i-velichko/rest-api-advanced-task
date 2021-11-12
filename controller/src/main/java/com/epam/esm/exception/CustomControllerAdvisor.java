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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

import static com.epam.esm.exception.CustomErrorMessageCode.ENTITY_NOT_FOUND;
import static com.epam.esm.exception.CustomErrorMessageCode.INVALID_REQUEST_VALUE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author Ivan Velichko
 * @date 04.10.2021 10:37
 */

@RestControllerAdvice
@PropertySource("classpath:error_code.properties")
public class CustomControllerAdvisor {
    private static final String UNDEFINED_DAO = "undefined dao";
    private final I18nManager i18nManager;
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_CODE = "errorCode";

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
    @Value("${not.valid.request.error.code}")
    private int NOT_VALID_REQUEST_CODE;

    @Autowired
    public CustomControllerAdvisor(I18nManager i18nManager) {
        this.i18nManager = i18nManager;
    }

    @ExceptionHandler(NoSuchEntityException.class)
    public ResponseEntity<Object> handleNoSuchEntityException(NoSuchEntityException e, Locale locale) {
        String localeMsg = i18nManager.getMessage(e.getMessage(), locale);
        return new ResponseEntity<>(createResponse(NO_SUCH_ENTITY_CODE, localeMsg), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return new ResponseEntity<>(createResponse(DATA_INTEGRITY_VIOLATION_CODE, e.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<Object> handleDuplicateEntityException(DuplicateEntityException e, Locale locale) {
        String localeMsg = i18nManager.getMessage(e.getMessage(), locale);
        return new ResponseEntity<>(createResponse(DUPLICATE_ENTITY_CODE, localeMsg), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = resolveBindingResultErrors(e.getBindingResult());
        return new ResponseEntity<>(createResponse(40001, message), BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(Locale locale) {
        return new ResponseEntity<>(createResponse(40001, i18nManager.getMessage(INVALID_REQUEST_VALUE, locale)), BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<Object> handler(ConstraintViolationException e) {
        return new ResponseEntity<>(createResponse(40001, e.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(IncorrectParamValueException.class)
    public ResponseEntity<Object> handleIncorrectParamValueException(IncorrectParamValueException e, Locale locale) {
        return new ResponseEntity<>(createResponse(NO_SUCH_PARAMETER_CODE, i18nManager.getMessage(e.getMessage(), locale)), BAD_REQUEST);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException e, Locale locale) {
        String msg = getEntityNameByMsg(e, ".", "Dao").toLowerCase(Locale.ROOT);
        String localeMsg = i18nManager.getMessage(ENTITY_NOT_FOUND + msg, locale);
        return new ResponseEntity<>(createResponse(EMPTY_RESULT_DATA_ACCESS_CODE, localeMsg), BAD_REQUEST);
    }

    private Map<String, Object> createResponse(int errorCode, String errorDescription) {
        Map<String, Object> response = new HashMap<>();
        response.put(ERROR_MESSAGE, errorDescription);
        response.put(ERROR_CODE, errorCode);
        return response;
    }

    private String resolveBindingResultErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(fr -> {
                    String field = fr.getField();
                    String validationMessage = fr.getDefaultMessage();
                    return String.format("'%s': %s", field, validationMessage);
                })
                .collect(Collectors.joining(", "));
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
