package com.product.product_service.outbound.ports.impl;

import com.product.product_service.mappers.ProductMapper;
import com.product.product_service.outbound.ports.ProductRepositoryOutboundPort;
import com.product.product_service.repositories.ProductRepository;
import com.product.restful.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductRepositoryOutboundPortImpl implements ProductRepositoryOutboundPort {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<Product> getProducts() {
        return productMapper.mapToProductResponseList(productRepository.findAll());
    }
}
