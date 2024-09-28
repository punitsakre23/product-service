package com.product.product_service.outbound.ports;

import com.product.product_service.dtos.ProductDto;
import com.product.restful.models.CreateProduct;
import com.product.restful.models.Product;
import java.util.List;

public interface ProductRepositoryOutboundPort {

  List<Product> getProducts();

  Product createAProduct(CreateProduct createProduct);

  boolean validateProductExists(ProductDto product);
}
