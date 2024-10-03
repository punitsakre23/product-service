package com.product.product_service.repositories;

import com.product.product_service.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
  boolean existsByProductIdAndBookingChannelAndProductGroupId(
      String productId, String bookingChannel, Integer productGroupId);
}
