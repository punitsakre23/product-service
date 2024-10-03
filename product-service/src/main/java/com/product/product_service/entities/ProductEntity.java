package com.product.product_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity extends AuditingEntity implements Serializable {

  @Serial private static final long serialVersionUID = -33615507434931014L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "product_id", nullable = false)
  @Size(max = 10)
  private String productId;

  @Column(name = "effective_from", nullable = false)
  private LocalDate effectiveFrom;

  @Column(name = "effective_to", nullable = false)
  private LocalDate effectiveTo;

  @Column(name = "booking_channel", nullable = false)
  @Size(max = 20)
  private String bookingChannel;

  @Column(name = "product_group_id", nullable = false)
  private Integer productGroupId;

  @Column(name = "is_platform_fee_applicable", nullable = false)
  @Size(max = 10)
  private String isPlatformFeeApplicable;

  @Column(name = "remarks")
  @Size(max = 500)
  private String remarks;

  @Column(name = "platform_fee_threshold_limit")
  private Double platformFeeThresholdLimit;

  @Column(name = "lower_platform_fee")
  private Double lowerPlatformFee;

  @Column(name = "higher_platform_fee")
  private Double higherPlatformFee;

  @Column(name = "available", nullable = false)
  @Builder.Default
  @NotNull
  private Boolean available = Boolean.TRUE;
}
