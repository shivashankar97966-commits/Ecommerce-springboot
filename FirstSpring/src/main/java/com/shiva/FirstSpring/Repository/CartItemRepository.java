package com.shiva.FirstSpring.Repository;

import com.shiva.FirstSpring.Model.Product;
import com.shiva.FirstSpring.Model.User;
import com.shiva.FirstSpring.Model.UserCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<UserCart, Long> {
    UserCart findByUserAndProduct(User user, Product product);
    Void deleteByUserAndProduct(User user, Product product);
    List<UserCart> getByUser(User user);
}
