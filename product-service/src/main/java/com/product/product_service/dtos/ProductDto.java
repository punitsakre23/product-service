package com.product.product_service.dtos;

import com.product.product_service.constants.ProductConstant;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable {

  @Serial private static final long serialVersionUID = -6134356395315167797L;

  @NotBlank private String productId;

  @NotNull private LocalDate effectiveFrom;

  @NotNull private LocalDate effectiveTo;

  @NotBlank
  @Pattern(regexp = "^(ANDROID|IOS|BADA|SYMBIAN)$", message = "invalid Booking Channel")
  private String bookingChannel;

  @NotNull private Integer productGroupId;

  @NotBlank
  @Pattern(regexp = "^(YES|NO|WAIVE)$", message = "Invalid Platform Fee Applicability value")
  private String isPlatformFeeApplicable;

  private String remarks;

  private Double platformFeeThresholdLimit;

  private Double lowerPlatformFee;

  private Double higherPlatformFee;

  @AssertTrue(message = "Effective From date must be before Effective To date")
  private boolean isEffectiveDatesValid() {
    return effectiveFrom.isBefore(effectiveTo);
  }

  @AssertTrue(
      message =
          "When Platform Fee Applicability is 'YES' then "
              + "platformFeeThresholdLimit, lowerPlatformFee & higherPlatformFee should not be null or less than 0")
  private boolean isPlatformFeeApplicable() {
    if (isPlatformFeeApplicable.equals(ProductConstant.APPLICABILITY_YES)) {
      return platformFeeThresholdLimit != null
          && platformFeeThresholdLimit >= 0
          && lowerPlatformFee != null
          && lowerPlatformFee >= 0
          && higherPlatformFee != null
          && higherPlatformFee >= 0;
    }
    return true;
  }
}
