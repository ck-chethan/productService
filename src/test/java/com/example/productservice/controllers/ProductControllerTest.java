package com.example.productservice.controllers;

import com.example.productservice.exceptions.ProductNotExistsException;
import com.example.productservice.models.Product;
import com.example.productservice.repositories.ProductRepository;
import com.example.productservice.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {
    @Autowired
    private ProductController productController;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private ProductRepository productRepository;

    @Test
    void testProductSameAsService() {
        // Arrange
        List<Product> products = new ArrayList<>();
        Product p1 = new Product();
        p1.setId(1L);
        p1.setTitle("Product 1");
        p1.setPrice(100.0);
        products.add(p1);

        Product p2 = new Product();
        p2.setId(2L);
        p2.setTitle("Product 2");
        p2.setPrice(200.0);
        products.add(p2);

        Product p3 = new Product();
        p3.setId(3L);
        p3.setTitle("Product 3");
        p3.setPrice(300.0);
        products.add(p3);

        List<Product> productListToPass = new ArrayList<>();
        for (Product product : products) {
            Product productToPass = new Product();
            productToPass.setId(product.getId());
            productToPass.setTitle(product.getTitle());
            productToPass.setPrice(product.getPrice());
            productListToPass.add(productToPass);
        }

        when(productService.getAllProducts()).thenReturn(productListToPass);

        // Act
        ResponseEntity<List<Product>> response = productController.getAllProducts();

        // Assert
        List<Product> productInResponse = response.getBody();

        assertEquals(products.size(), productInResponse.size());
        for (int i = 0; i < products.size(); i++) {
            Product expectedProduct = products.get(i);
            Product actualProduct = productInResponse.get(i);

            assertEquals(expectedProduct.getId(), actualProduct.getId());
            assertEquals(expectedProduct.getTitle(), actualProduct.getTitle());
            assertEquals(expectedProduct.getPrice(), actualProduct.getPrice());
        }
    }

    @Test
    void testNonExistingProduct() {
        // Arrange
        Long nonExistingProductId = 999L;
        when(productService.getProductById(nonExistingProductId))
                .thenThrow(new ProductNotExistsException("Product not found with id: " + nonExistingProductId));

        // Act & Assert
        assertThrows(ProductNotExistsException.class, () -> {
            productController.getProductById(nonExistingProductId);
        });



    }
}