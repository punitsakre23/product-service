package com.product.product_service.exceptions;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

  public BusinessException(String message) {
    super(message);
  }
}
