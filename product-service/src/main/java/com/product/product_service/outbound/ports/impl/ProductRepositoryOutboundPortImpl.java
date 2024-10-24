package com.product.product_service.outbound.ports.impl;

import com.product.product_service.constants.ProductConstant;
import com.product.product_service.dtos.ProductDto;
import com.product.product_service.exceptions.NotFoundException;
import com.product.product_service.mappers.ProductMapper;
import com.product.product_service.outbound.ports.ProductRepositoryOutboundPort;
import com.product.product_service.repositories.ProductRepository;
import com.product.restful.models.CreateProduct;
import com.product.restful.models.Product;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductRepositoryOutboundPortImpl implements ProductRepositoryOutboundPort {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  @Override
  public List<Product> getProducts() {
    return productMapper.mapToProductResponseList(productRepository.findAll());
  }

  @Override
  public Product createAProduct(CreateProduct createProduct) {
    var product = productMapper.mapToProductEntity(createProduct);
    return productMapper.mapToProductResponse(productRepository.save(product));
  }

  @Override
  public boolean validateProductExists(ProductDto product) {
    return productRepository.existsByProductIdAndBookingChannelAndProductGroupId(
        product.getProductId(), product.getBookingChannel(), product.getProductGroupId());
  }

  @Override
  public Product getProductById(Long id) {
    var product =
        productRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(ProductConstant.NOT_FOUND_EXCEPTION));
    return productMapper.mapToProductResponse(product);
  }
}
