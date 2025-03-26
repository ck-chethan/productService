package com.example.productservice.services;

import com.example.productservice.models.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product saveProduct(Product product);
    Product updateProduct(Long id, Product product);
    Product replaceProduct(Long id, Product product);
    boolean deleteProduct(Long id);

}
