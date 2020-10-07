package com.underscore.ekartapp.exceptionhandler;

import com.underscore.ekartapp.util.LanguageUtil;
import com.underscore.ekartapp.view.ResponseView;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class RestExceptionHandler {

    @Autowired
    private LanguageUtil languageUtil;

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseView formValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());
        String errorCode = errors.get(0);
        String errorMessage = null;
        try {
            errorMessage = languageUtil.getTranslatedText(errors.get(0));
        } catch (Exception e) {

        }
        if (errorMessage == null) {
            errorMessage = errorCode;
        }
        return new ResponseView(null, errorMessage, errorCode);
    }

    @ExceptionHandler(value = {ResponseStatusException.class})
    public ResponseEntity<Object> responseStatus(ResponseStatusException ex) {
        String errorCode = ex.getReason();
        String errorMessage = null;
        try {
            errorMessage = languageUtil.getTranslatedText(errorCode);
        } catch (Exception e) {

        }
        if (errorMessage == null) {
            String field = null;
            if (errorCode != null && errorCode.contains("java.lang.NumberFormatException")) {
                field = errorCode.replaceAll("^.*for property '(.*)';.*$", "$1");
            }
            if (field != null && !"".equals(field)) {
                errorCode = "invalid " + field;
            }
            errorMessage = errorCode;
        }
        ResponseView responseView = new ResponseView(null, errorMessage, errorCode);
        return new ResponseEntity<>(responseView, null, ex.getStatus());
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseView methodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return new ResponseView(null, "MethodNotSupported", ex.getMessage());
    }

    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseView mediaTypeNotAccepted(HttpMediaTypeNotSupportedException ex) {
        return new ResponseView(null, "mediaTypeNotSupported", ex.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseView unKnownException(Exception ex) {
        return new ResponseView(null, "BAD_REQUEST", ex.getMessage());
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseView illegalException(IllegalArgumentException ex) {
        return new ResponseView(null, "Illegal Argument", "Illegal Argument used");
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseView validException(HttpMessageNotReadableException ex) {
        return new ResponseView(null, "HttpMessageNotReadableException", "HttpMessageNotReadableException");
    }

}
