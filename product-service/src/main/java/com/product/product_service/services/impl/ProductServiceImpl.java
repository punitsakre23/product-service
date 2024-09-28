package com.product.product_service.services.impl;

import com.product.product_service.constants.ProductConstant;
import com.product.product_service.dtos.ProductDto;
import com.product.product_service.exceptions.BadRequestException;
import com.product.product_service.mappers.ProductMapper;
import com.product.product_service.outbound.ports.ProductRepositoryOutboundPort;
import com.product.product_service.services.ProductService;
import com.product.restful.models.CreateProduct;
import com.product.restful.models.Product;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepositoryOutboundPort productRepositoryOutboundPort;
  private final ProductMapper productMapper;
  private final Validator validator;

  @Override
  public List<Product> getProducts() {
    return productRepositoryOutboundPort.getProducts();
  }

  @Override
  public Product createAProduct(CreateProduct createProduct) {
    var productDto = productMapper.mapToProductDto(createProduct);
    validateRequest(productDto);
    validateProductExists(productDto);
    return productRepositoryOutboundPort.createAProduct(createProduct);
  }

  /**
   * Check if Product Already Exists
   *
   * @param product request dto
   */
  private void validateProductExists(ProductDto product) {
    var exists = productRepositoryOutboundPort.validateProductExists(product);
    if (exists) {
      throw new BadRequestException(ProductConstant.BAD_REQUEST_EXCEPTION);
    }
  }

  /**
   * Constraint Violation check
   *
   * @param dto Generic request dto
   * @param <T> Generic return type
   */
  private <T> void validateRequest(T dto) {
    Set<ConstraintViolation<T>> violations = validator.validate(dto);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
