package com.example.productservice;

import com.example.productservice.models.Product;
import com.example.productservice.repositories.ProductRepository;
import com.example.productservice.repositories.projections.ProductWithIdAndtitle;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;
import java.util.Optional;

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
//        productRepository.findByTitleContaining("test");
//        List<ProductWithIdAndtitle> productsWithIdAndTitle = productRepository.findAllProductsIdAndTitle();
//        for (ProductWithIdAndtitle productWithIdAndTitle : productsWithIdAndTitle
//             ) {
//            System.out.println(productWithIdAndTitle.getId() + " " + productWithIdAndTitle.getTitle());
//        }
//
//        List<Product> products = productRepository.findAProductWithIdNativeQuery();
//        System.out.println(products.toString());
//        List<ProductWithIdAndtitle> productsWithIdAndTitleNativeQuery = productRepository.findAProductWithIdNativeQueryIdTitle(1L);
////        System.out.println(productsWithIdAndTitleNativeQuery.toString());
//        for (ProductWithIdAndtitle productWithIdAndTitle : productsWithIdAndTitleNativeQuery
//        ) {
//            System.out.println(productWithIdAndTitle.getId() + " " + productWithIdAndTitle.getTitle());
//        }

        Optional<Product> productOptional = productRepository.findById(702L);
        if (productOptional.isPresent()) {
            System.out.println(productOptional.get().getTitle());
            System.out.println(productOptional.get().getCategory().getName());
        }
    }

}
