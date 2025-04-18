package org.example.shoppingcartapi.service;

import org.example.shoppingcartapi.dto.request.CartItemRequest;
import org.example.shoppingcartapi.dto.response.CartItemResponse;
import org.example.shoppingcartapi.exception.ItemNotFoundException;
import org.example.shoppingcartapi.model.CartItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CartService {
    //TO-DO : Move this to a repository instead
    private final ConcurrentHashMap<Integer, CartItem> cartItems = new ConcurrentHashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public List<CartItemResponse> getAllItems() {
        return cartItems.values().stream()
                .map(item -> new CartItemResponse(
                        item.getId(),
                        item.getName(),
                        item.getQuantity(),
                        item.getPrice()
                ))
                .toList();
    }

    public double getTotalPrice() {
        return cartItems.values().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    public CartItemResponse addItem(CartItemRequest request) {
        CartItem item = new CartItem(
                idCounter.getAndIncrement(),
                request.name(),
                request.quantity(),
                request.price()
        );
        cartItems.put(item.getId(), item);
        return new CartItemResponse(
                item.getId(),
                item.getName(),
                item.getQuantity(),
                item.getPrice()
        );
    }

    public void deleteItem(int id) {
        if (cartItems.remove(id) == null) {
            throw new ItemNotFoundException("Item with ID " + id + " not found");
        }
    }

    public CartItemResponse updateItem(int id, CartItemRequest request) {
        CartItem item = Optional.ofNullable(cartItems.get(id))
                .orElseThrow(() -> new ItemNotFoundException("Item not found"));

        // Full update: Replace all fields
        item.setName(request.name());
        item.setPrice(request.price());
        item.setQuantity(request.quantity());

        return new CartItemResponse(
                item.getId(),
                item.getName(),
                item.getQuantity(),
                item.getPrice()
        );
    }
}