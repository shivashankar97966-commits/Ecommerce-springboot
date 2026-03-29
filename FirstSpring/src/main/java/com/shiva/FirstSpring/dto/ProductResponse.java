package com.shiva.FirstSpring.dto;

import lombok.Data;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private String category;
    private String imageUrl;
    private Boolean active;
}
