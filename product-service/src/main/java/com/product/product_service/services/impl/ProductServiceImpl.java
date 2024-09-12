package com.product.product_service.services.impl;

import com.product.product_service.outbound.ports.ProductRepositoryOutboundPort;
import com.product.product_service.services.ProductService;
import com.product.restful.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepositoryOutboundPort productRepositoryOutboundPort;

    @Override
    public List<Product> getProducts() {
        return productRepositoryOutboundPort.getProducts();
    }
}
