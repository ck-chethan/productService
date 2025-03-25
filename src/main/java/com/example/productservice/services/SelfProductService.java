package com.example.productservice.services;

import com.example.productservice.exceptions.ProductNotExistsException;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import com.example.productservice.repositories.CategoryRepository;
import com.example.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("selfProductService")
//@Primary -> This will also works but easy to make mistake if someone make other class as Primary
public class SelfProductService  implements ProductService {
    private ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public SelfProductService(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public Product getProductById(Long id) throws ProductNotExistsException {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new ProductNotExistsException("Product with id " + id + " does not exist");
        }
        return productOptional.get();
    }

    @Override
    public Product saveProduct(Product product) {
        Category category = product.getCategory();
        Optional<Category> categoryOptional = categoryRepository.findByName(category.getName());
        if (categoryOptional.isEmpty()) {
            Category newCategory = categoryRepository.save(category);
            product.setCategory(newCategory);
        } else {
            product.setCategory(categoryOptional.get());
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return null;
    }

    @Override
    public Product replaceProduct(Product product) {
        return null;
    }

    @Override
    public boolean deleteProduct(Long id) {
        return false;
    }
}
