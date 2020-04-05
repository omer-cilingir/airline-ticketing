package com.omer.ticketing.web.advice;

import com.omer.ticketing.exception.ErrorEnum;
import com.omer.ticketing.exception.TicketingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@ResponseBody
@ControllerAdvice
public class GlobalControllerAdvice {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException ex) {
        final String message = ex.getBindingResult().getFieldErrors().stream()
                .filter(Objects::nonNull)
                .filter(f -> !StringUtils.isEmpty(f.getField()))
                .map(f -> f.getField().concat(" ").concat("wrong or empty!"))
                .collect(Collectors.joining(";"));
        return ErrorResponse.builder()
                .code(ErrorEnum.REQUIRED_PARAMETERS_CANNOT_BE_NULL.getCode())
                .message(message)
                .build();
    }

    @ExceptionHandler(TicketingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDomainException(TicketingException ex) {
        return ErrorResponse.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handle(Exception ex, HttpServletRequest httpServletRequest) {
        log.error(httpServletRequest.getServletPath(), ex);
        return ErrorResponse.builder()
                .code(ErrorEnum.GENERAL_EXCEPTION.getCode())
                .message(ErrorEnum.GENERAL_EXCEPTION.getMessage())
                .build();
    }
}
