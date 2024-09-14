package com.product.product_service.services;

import com.product.restful.models.CreateProduct;
import com.product.restful.models.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts();

    Product createAProduct(CreateProduct createProduct);
}
