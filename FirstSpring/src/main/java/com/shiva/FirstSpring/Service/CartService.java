package com.shiva.FirstSpring.Service;

import com.shiva.FirstSpring.Model.Product;
import com.shiva.FirstSpring.Model.User;
import com.shiva.FirstSpring.Model.UserCart;
import com.shiva.FirstSpring.Repository.CartItemRepository;
import com.shiva.FirstSpring.Repository.ProductRepository;
import com.shiva.FirstSpring.Repository.UserRepository;
import com.shiva.FirstSpring.dto.CartItemRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public boolean addToCart(String userId, CartItemRequest cartItemRequest) {
        //check if product is existing
        Optional<Product> productOption = productRepository.findById(cartItemRequest.getProductId());
        if(productOption.isEmpty()) {
            return false;
        }
        Product product = productOption.get();
        if(product.getStock() < cartItemRequest.getQuantity()) {
            return false;
        }

        //check if user is existing
        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        if(user.isEmpty()) {
            return false;
        }

        User userValue = user.get();

        //if product is already in cart update the quantity
        //else add the product to the cart
        UserCart existingCartItem = cartItemRepository.findByUserAndProduct(userValue, product);
        if(existingCartItem != null)
        {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        } else {
            UserCart cartItem = new UserCart();
            cartItem.setUser(userValue);
            cartItem.setProduct(product);
            cartItem.setQuantity(cartItemRequest.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getQuantity())));
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    public boolean removeProduct(String userId, Long productId) {
        Optional<User> user = userRepository.findById(Long.valueOf(userId));
        if(user.isEmpty()) {
            return false;
        }

        Optional<Product> product = productRepository.findById(productId);
        if(product.isEmpty()) {
            return false;
        }

        if(user.isPresent() && product.isPresent()) {
            cartItemRepository.deleteByUserAndProduct(user.get(), product.get());
            return true;
        }
        return false;
    }

    public List<UserCart> getCartItems(String userId) {
        return userRepository.findById(Long.valueOf(userId)).
                map(cartItemRepository::getByUser).
                orElse(List.of());
    }
}
