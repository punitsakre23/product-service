package com.product.product_service.exceptions;

public class BadRequestException extends BusinessException {
  public BadRequestException(String message) {
    super(message);
  }
}
