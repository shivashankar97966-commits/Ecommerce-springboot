package com.shiva.FirstSpring.Controller;

import com.shiva.FirstSpring.Model.UserCart;
import com.shiva.FirstSpring.Service.CartService;
import com.shiva.FirstSpring.dto.CartItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    ResponseEntity<String> addToCart(
            @RequestHeader("X-user-Id") String userId,
            @RequestBody CartItemRequest request) {
        if(!cartService.addToCart(userId, request)){
            return ResponseEntity.badRequest().body("Product or User not Found");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/Items/{productId}")
    ResponseEntity<Void> deleteFromCart(
            @RequestHeader("X-user-Id") String userId,
            @PathVariable Long productId) {
        boolean deleted = cartService.removeProduct(userId, productId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    ResponseEntity<List<UserCart>> getCartItems(@RequestHeader("X-user-Id") String userId) {
        return ResponseEntity.ok(cartService.getCartItems(userId));
    }

}
