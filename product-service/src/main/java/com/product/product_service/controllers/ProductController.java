package com.product.product_service.controllers;

import com.product.product_service.services.ProductService;
import com.product.restful.apis.ProductControllerApi;
import com.product.restful.models.CreateProduct;
import com.product.restful.models.GetAllProducts;
import com.product.restful.models.Product;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController implements ProductControllerApi {

  private final ProductService productService;

  @Override
  public ResponseEntity<GetAllProducts> getAllProducts() {
    var listOfProducts = productService.getProducts();
    GetAllProducts allProducts =
        new GetAllProducts().data(listOfProducts).timestamp(LocalDateTime.now());
    return ResponseEntity.ok(allProducts);
  }

  @Override
  public ResponseEntity<Product> addProduct(CreateProduct createProduct) {
    log.info("Add a product: {}", createProduct.getProductId());
    return ResponseEntity.ok(productService.createAProduct(createProduct));
  }

  @Override
  public ResponseEntity<Product> getProductDetail(Long id) {
    log.info("Get Product Info: {}", id);
    return ResponseEntity.ok(productService.getProductById(id));
  }
}
