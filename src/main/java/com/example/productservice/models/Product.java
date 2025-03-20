package com.example.productservice.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Data
public class Product {
    private Long id;
    private String title;
    private double price;
    private Category category;
    private String description;
    private String imageUrl;
}
