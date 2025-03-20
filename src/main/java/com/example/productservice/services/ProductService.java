package com.example.productservice.services;

import com.example.productservice.models.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product saveProduct(Product product);
    Product updateProduct(Product product);
    Product replaceProduct(Product product);
    void deleteProduct(Long id);
}
