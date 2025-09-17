package com.api.productmanagementapi.dtos;

import com.api.productmanagementapi.entity.Product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record ProductPatch(
        // If provided, must not be blank; null means "don’t change"
        @Pattern(regexp = ".*\\S.*", message = "Name cannot be blank")
        String name,

        // If provided, must be >= 0
        @DecimalMin(value = "0.00", inclusive = true, message = "Price cannot be negative")
        BigDecimal price,

        // If provided, must be >= 0
        @Min(value = 0, message = "Quantity cannot be negative")
        Integer quantity
) {
    /** Apply only non-null fields to the target entity (in-place mutate). */
    public void applyPartially(Product target) {
        if (name != null) target.setName(name);
        if (price != null) target.setPrice(price);
        if (quantity != null) target.setQuantity(quantity);
    }
}
