package com.example.productservice.services;

import com.example.productservice.dtos.ProductResponse;
import com.example.productservice.exceptions.ProductNotExistsException;
import com.example.productservice.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Page<ProductResponse> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir);
    ProductResponse getProductById(Long id) throws ProductNotExistsException;
    ProductResponse saveProduct(Product product);
    ProductResponse updateProduct(Long id, Product product);
    ProductResponse replaceProduct(Long id, Product product);
    boolean deleteProduct(Long id);

}
