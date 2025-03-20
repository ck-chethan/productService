package com.example.productservice.dtos;

import lombok.*;

@Data
public class FakestoreProductDto {
    private Long id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
}
