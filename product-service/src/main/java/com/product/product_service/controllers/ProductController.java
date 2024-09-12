package com.product.product_service.controllers;

import com.product.restful.apis.ProductControllerApi;
import com.product.restful.models.GetAllProducts;
import org.springframework.http.ResponseEntity;

public class ProductController implements ProductControllerApi {

    @Override
    public ResponseEntity<GetAllProducts> getAllProducts() {

        return null;
    }
}
