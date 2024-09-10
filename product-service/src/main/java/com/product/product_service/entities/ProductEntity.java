package com.product.product_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProductEntity extends AuditingEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -33615507434931014L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false, unique = true)
    @NotBlank
    @Size(max = 10)
    private String productId;

    @Column(name = "effective_from", nullable = false)
    @NotNull
    private LocalDate effectiveFrom;

    @Column(name = "effective_to", nullable = false)
    @NotNull
    private LocalDate effectiveTo;

    @Column(name = "booking_channel", nullable = false)
    @NotBlank
    @Size(max = 20)
    private String bookingChannel;

    @Column(name = "product_group_id", nullable = false)
    @NotNull
    private Integer productGroupId;

    @Column(name = "is_platform_fee_applicable", nullable = false)
    @NotBlank
    @Pattern(regexp = "^(YES|NO|WAIVE)$", message = "must be 'YES', 'NO', or 'WAIVE'")
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
    @NotNull
    private Boolean available;

    @Column(name = "created_by", nullable = false)
    @NotBlank
    @Size(max = 50)
    private String createdBy;

    @Column(name = "created_date", nullable = false)
    @NotNull
    @PastOrPresent
    private LocalDateTime createdDate;

    @Column(name = "updated_by")
    @Size(max = 255)
    private String updatedBy;

    @Column(name = "updated_date")
    @FutureOrPresent
    private LocalDateTime updatedDate;

    @AssertTrue(message = "Effective From date must be before Effective To date")
    private boolean isEffectiveDatesValid() {
        return effectiveFrom == null || effectiveTo == null || effectiveFrom.isBefore(effectiveTo);
    }
}
