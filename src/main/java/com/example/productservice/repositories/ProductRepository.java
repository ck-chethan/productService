package com.example.productservice.repositories;

import com.example.productservice.models.Product;
import com.example.productservice.repositories.projections.ProductWithIdAndtitle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByTitleContaining(String name);

    Page<Product> findAll(Pageable pageable);

    Page<Product> findByCategory_Id(Long Id, Pageable pageable);


    Long deleteByTitle(String title);

    Optional<Product> findById(Long id);

    Product save(Product product);

    @Query("SELECT p.id as id, p.title as title FROM Product p")
    List<ProductWithIdAndtitle> findAllProductsIdAndTitle();

    @Query(value = "SELECT * FROM product p WHERE p.id > 1", nativeQuery = true)
    List<Product> findAProductWithIdNativeQuery();

    @Query(value = "SELECT p.id as id, p.title as title FROM product p WHERE p.id > :id", nativeQuery = true)
    List<ProductWithIdAndtitle> findAProductWithIdNativeQueryIdTitle(@Param("id") Long id);
}
