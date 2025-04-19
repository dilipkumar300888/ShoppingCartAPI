package org.example.shoppingcartapi.dto.response;

public record CartItemResponse(
        int id,
        String name,
        int quantity,
        double price
) {}
