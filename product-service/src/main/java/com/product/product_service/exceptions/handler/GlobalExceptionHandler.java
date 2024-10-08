package com.product.product_service.exceptions.handler;

import com.product.product_service.exceptions.BadRequestException;
import com.product.restful.models.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ConstraintViolationException.class)
  public ConstraintViolationErrorResponse handleConstraintViolationException(
      final ConstraintViolationException exception, final HttpServletRequest request) {
    log.error("inside constraint violation exception handler: {} ", exception.getMessage());
    Map<String, List<String>> errorMap = new HashMap<>();
    exception
        .getConstraintViolations()
        .forEach(
            violation -> {
              var fieldName = violation.getPropertyPath().toString();
              var errorMessage = violation.getMessage();
              errorMap.computeIfAbsent(fieldName, error -> new ArrayList<>()).add(errorMessage);
            });
    List<ConstraintViolationFields> errorFields = new ArrayList<>();
    errorMap.forEach(
        (fieldName, errorMessages) ->
            errorFields.add(
                new ConstraintViolationFields().name(fieldName).message(errorMessages)));
    return new ConstraintViolationErrorResponse()
        .error(
            new ConstraintViolationError()
                .code(exception.getClass().getSimpleName())
                .message("InvalidRequestContent")
                .data(new ConstraintViolationErrorData().fields(errorFields)))
        .traceId("0")
        .path(request.getRequestURI())
        .timestamp(LocalDateTime.now());
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(BadRequestException.class)
  public BadRequestErrorResponse handleConstraintViolationException(
      final BadRequestException exception, final HttpServletRequest request) {
    log.error("inside bad request exception handler: {} ", exception.getMessage());
    return new BadRequestErrorResponse()
        .traceId("0")
        .error(
            new BadRequestError()
                .code(exception.getClass().getSimpleName())
                .message("Resource not found"))
        .path(request.getRequestURI())
        .timestamp(LocalDateTime.now());
  }
}
