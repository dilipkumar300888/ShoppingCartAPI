package org.example.shoppingcartapi.dto.request;

import jakarta.validation.constraints.*;

public record CartItemRequest(
        @NotBlank(message = "Name cannot be blank")
        String name,

        @Min(value = 1, message = "Quantity must be at least 1")
        int quantity,

        @Positive(message = "Price must be positive")
        double price
) {}