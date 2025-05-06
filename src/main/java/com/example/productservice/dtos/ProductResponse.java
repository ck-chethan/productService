package com.example.productservice.dtos;

import com.example.productservice.models.Product;

public record ProductResponse(Long id, String title, Double price, String description, String categoryName, String imageUrl) {
    public static ProductResponse from(Product p) {
        return new ProductResponse(
                p.getId(), p.getTitle(), p.getPrice(), p.getDescription(), p.getCategory().getName(), p.getImageUrl()
        );
    }
}
