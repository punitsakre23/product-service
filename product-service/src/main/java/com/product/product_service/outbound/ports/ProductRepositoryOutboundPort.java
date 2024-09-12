package com.product.product_service.outbound.ports;

import com.product.restful.models.Product;

import java.util.List;

public interface ProductRepositoryOutboundPort {

    List<Product> getProducts();
}