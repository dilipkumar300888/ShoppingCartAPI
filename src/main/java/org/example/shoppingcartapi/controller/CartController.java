package org.example.shoppingcartapi.controller;

import jakarta.validation.Valid;
import org.example.shoppingcartapi.dto.request.CartItemRequest;
import org.example.shoppingcartapi.dto.response.CartItemResponse;
import org.example.shoppingcartapi.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {
    // NOTE: We should have stateless controllers as these are usually singleton; move state to a service layer
    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public List<CartItemResponse> getAllItems() {
        return cartService.getAllItems();
    }

    @GetMapping("/total")
    public double getTotalPrice() {
        return cartService.getTotalPrice();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartItemResponse addItem(@Valid @RequestBody CartItemRequest request) {
        return cartService.addItem(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable int id) {
        cartService.deleteItem(id);
    }

    @PutMapping("/{id}")
    public CartItemResponse updateItem(@PathVariable int id, @Valid @RequestBody CartItemRequest request) {
        return cartService.updateItem(id, request);
    }
}
