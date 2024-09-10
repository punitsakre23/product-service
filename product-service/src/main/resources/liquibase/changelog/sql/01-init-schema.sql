--liquibase formatted sql

--changeset punitsakre23:1
--initial table setup product
CREATE TABLE product (
    id                              BIGSERIAL PRIMARY KEY,
    product_id                      VARCHAR(10) NOT NULL UNIQUE,
    effective_from                  DATE NOT NULL,
    effective_to                    DATE NOT NULL,
    booking_channel                 VARCHAR(20) NOT NULL,
    product_group_id                INT NOT NULL,
    is_platform_fee_applicable      VARCHAR(10) NOT NULL,
    remarks                         TEXT,
    platform_fee_threshold_limit    DECIMAL(10, 2) DEFAULT 0,
    lower_platform_fee              DECIMAL(10, 2) DEFAULT 0,
    higher_platform_fee             DECIMAL(10, 2) DEFAULT 0,
    available                       BOOLEAN NOT NULL DEFAULT true,
    created_by                      VARCHAR(50) NOT NULL,
    created_date                    TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_by                      VARCHAR(50),
    updated_date                    TIMESTAMP,
    -- Check constraint to ensure effective_from is before effective_to
    CONSTRAINT effective_dates_check
    CHECK (effective_from <= effective_to),
    -- Check constraint to enforce specific values for is_platform_fee_applicable
    CONSTRAINT is_platform_fee_applicable_check
    CHECK (is_platform_fee_applicable IN ('YES', 'NO', 'WAIVE')
);

-- Add comments for each column
COMMENT ON COLUMN product.id IS 'Unique identifier for the product';
COMMENT ON COLUMN product.product_id IS 'Product ID';
COMMENT ON COLUMN product.effective_from IS 'Effective start date of the product';
COMMENT ON COLUMN product.effective_to IS 'Effective end date of the product';
COMMENT ON COLUMN product.booking_channel IS 'Channel through which the product is booked';
COMMENT ON COLUMN product.product_group_id IS 'Group ID of the product';
COMMENT ON COLUMN product.is_platform_fee_applicable IS 'Indicates if platform fee is applicable';
COMMENT ON COLUMN product.remarks IS 'Additional remarks regarding the product';
COMMENT ON COLUMN product.platform_fee_threshold_limit IS 'Threshold limit for platform fee';
COMMENT ON COLUMN product.lower_platform_fee IS 'Lower limit of platform fee';
COMMENT ON COLUMN product.higher_platform_fee IS 'Higher limit of platform fee';
COMMENT ON COLUMN product.available IS 'Availability status of the product';
COMMENT ON COLUMN product.created_by IS 'User or system that created the product';
COMMENT ON COLUMN product.created_date IS 'Date and time when the product was created';
COMMENT ON COLUMN product.updated_by IS 'User or system that last updated the product';
COMMENT ON COLUMN product.updated_date IS 'Date and time when the product was last updated';

-- Rollback for product table
--rollback
DROP TABLE product;
