package org.example.shoppingcartapi.controller;

import org.example.shoppingcartapi.model.CartItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartItemController {
    private List<CartItem> cartItems = new ArrayList<CartItem>();
    private int idCounter = 1;

    @GetMapping
    public List<CartItem> getCartItems() {
        return cartItems;
    }

    @GetMapping("/total")
    public double getTotalPrice() {
        return cartItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }
    @PostMapping
    public CartItem addCartItem(@RequestBody CartItem cartItem) {
        cartItem.setId(idCounter++);
        cartItems.add(cartItem);
        return cartItem;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCartItem(@PathVariable int id) {
        boolean removed = cartItems.removeIf(cartItem -> cartItem.getId() == id);
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.status(404).body("Item not found");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateQuantity(@PathVariable int id, @RequestBody CartItem cartItem) {
        for (CartItem item : cartItems) {
            if (item.getId() == id) {
                item.setQuantity(cartItem.getQuantity());
                return ResponseEntity.ok(item);
            }
        }
        return ResponseEntity.status(404).body("Item not found");
    }


}
