package com.product.product_service.exceptions.handler;

import com.product.product_service.exceptions.BadRequestException;
import com.product.product_service.exceptions.InternalServerException;
import com.product.product_service.exceptions.NotFoundException;
import com.product.restful.models.BadRequestError;
import com.product.restful.models.BadRequestErrorResponse;
import com.product.restful.models.ConstraintViolationError;
import com.product.restful.models.ConstraintViolationErrorData;
import com.product.restful.models.ConstraintViolationErrorResponse;
import com.product.restful.models.ConstraintViolationFields;
import com.product.restful.models.InternalServerError;
import com.product.restful.models.InternalServerErrorResponse;
import com.product.restful.models.NotFoundError;
import com.product.restful.models.NotFoundErrorResponse;
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
  public BadRequestErrorResponse handleBadRequestException(
      final BadRequestException exception, final HttpServletRequest request) {
    log.error("inside bad request exception handler: {} ", exception.getMessage());
    return new BadRequestErrorResponse()
        .traceId("0")
        .error(
            new BadRequestError()
                .code(exception.getClass().getSimpleName())
                .message(exception.getMessage()))
        .path(request.getRequestURI())
        .timestamp(LocalDateTime.now());
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public NotFoundErrorResponse handleNotFoundException(
          final BadRequestException exception, final HttpServletRequest request) {
    log.error("inside not found exception handler: {} ", exception.getMessage());
    return new NotFoundErrorResponse()
            .traceId("0")
            .error(
                    new NotFoundError()
                            .code(exception.getClass().getSimpleName())
                            .message(exception.getMessage()))
            .path(request.getRequestURI())
            .timestamp(LocalDateTime.now());
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(InternalServerException.class)
  public InternalServerErrorResponse handleInternalServerException(
          final InternalServerException exception, final HttpServletRequest request) {
    log.error("inside internal server exception handler: {} ", exception.getMessage());
    return new InternalServerErrorResponse()
            .traceId("0")
            .error(
                    new InternalServerError()
                            .code(exception.getClass().getSimpleName())
                            .message(exception.getMessage()))
            .path(request.getRequestURI())
            .timestamp(LocalDateTime.now());
  }

  @ResponseBody
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public InternalServerErrorResponse handleException(
          final Exception exception, final HttpServletRequest request) {
    log.error("inside exception handler: {} ", exception.getMessage());
    return new InternalServerErrorResponse()
            .traceId("0")
            .error(
                    new InternalServerError()
                            .code(exception.getClass().getSimpleName())
                            .message(exception.getMessage()))
            .path(request.getRequestURI())
            .timestamp(LocalDateTime.now());
  }
}
