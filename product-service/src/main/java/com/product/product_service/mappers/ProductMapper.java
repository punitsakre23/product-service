package com.product.product_service.mappers;

import com.product.product_service.dtos.ProductDto;
import com.product.product_service.entities.ProductEntity;
import com.product.restful.models.CreateProduct;
import com.product.restful.models.Product;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  List<Product> mapToProductResponseList(List<ProductEntity> productEntitiesList);

  ProductEntity mapToProductEntity(CreateProduct createProduct);

  Product mapToProductResponse(ProductEntity productEntity);

  ProductDto mapToProductDto(CreateProduct createProduct);
}
