package com.product.product_service.controllers;

import com.product.product_service.services.ProductService;
import com.product.restful.apis.ProductControllerApi;
import com.product.restful.models.GetAllProducts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductControllerApi {

    private final ProductService productService;

    @Override
    public ResponseEntity<GetAllProducts> getAllProducts() {
        var listOfProducts = productService.getProducts();
        GetAllProducts allProducts = new GetAllProducts().data(listOfProducts).timestamp(LocalDateTime.now());
        return ResponseEntity.ok(allProducts);
    }
}
