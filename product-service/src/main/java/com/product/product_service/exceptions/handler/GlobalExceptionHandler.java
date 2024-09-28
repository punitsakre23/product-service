package com.product.product_service.exceptions.handler;

import com.product.restful.models.BadRequestError;
import com.product.restful.models.BadRequestErrorData;
import com.product.restful.models.BadRequestErrorResponse;
import com.product.restful.models.BadRequestFields;
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
  public BadRequestErrorResponse handleConstraintViolationException(
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
    List<BadRequestFields> errorFields = new ArrayList<>();
    errorMap.forEach(
        (fieldName, errorMessages) ->
            errorFields.add(new BadRequestFields().name(fieldName).message(errorMessages)));
    return new BadRequestErrorResponse()
        .error(
            new BadRequestError()
                .code(exception.getClass().getSimpleName())
                .message("InvalidRequestContent")
                .data(new BadRequestErrorData().fields(errorFields)))
        .traceId("0")
        .path(request.getRequestURI())
        .timestamp(LocalDateTime.now());
  }
}
