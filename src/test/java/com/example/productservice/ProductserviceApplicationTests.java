package com.example.productservice;

import com.example.productservice.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

@SpringBootTest
class ProductserviceApplicationTests {
    @Autowired
    private ProductRepository productRepository;
    @Test
    void contextLoads() {
    }

    @Test
    @Transactional
    @Commit
    void testQueries() {
        productRepository.findByTitleContaining("test");
    }
}
