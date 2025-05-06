package com.example.productservice.services;

import com.example.productservice.dtos.FakestoreProductDto;
import com.example.productservice.dtos.ProductResponse;
import com.example.productservice.exceptions.ProductNotExistsException;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import com.example.productservice.repositories.CategoryRepository;
import com.example.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Primary
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

    private ProductResponse convertProductToProductResponse(Product productDto) {
        return new ProductResponse(productDto.getId(), productDto.getTitle(), productDto.getPrice(), productDto.getDescription(), productDto.getCategory().getName(), productDto.getImageUrl());
    }

    @Override
    public Page<ProductResponse> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = Sort.by(sortBy);
        sort = sortDir.equalsIgnoreCase("desc") ? sort.descending() : sort.ascending();
        sort = sort.and(Sort.by("price").ascending());
        Optional<Page<Product>> productsOptional = Optional.of(productRepository.findAll(PageRequest.of(pageNumber, pageSize, sort)));
        if (productsOptional.isEmpty()) {
            throw new ProductNotExistsException("No products found");
        }
        return productsOptional.get().map(ProductResponse::from);

    }

    @Override
    public ProductResponse getProductById(Long id) throws ProductNotExistsException {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new ProductNotExistsException("Product with id " + id + " does not exist");
        }
        return convertProductToProductResponse(productOptional.get());
    }

    @Override
    public ProductResponse saveProduct(Product product) {
        Category category = product.getCategory();
        Optional<Category> categoryOptional = categoryRepository.findByName(category.getName()) ;
        if (categoryOptional.isEmpty()) {
//            Category newCategory = categoryRepository.save(category);
//            product.setCategory(newCategory);
        } else {
            product.setCategory(categoryOptional.get());
        }
        return convertProductToProductResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse updateProduct(Long id, Product product) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new ProductNotExistsException("Product with id " + id + " does not exist");
        }
        Product updatedProduct = productOptional.get();
        if (product.getTitle() != null) {
            updatedProduct.setTitle(product.getTitle());
        }
        if (product.getDescription() != null) {
            updatedProduct.setDescription(product.getDescription());
        }
        if (product.getPrice() != null) {
            updatedProduct.setPrice(product.getPrice());
        }
        if (product.getCategory() != null) {
            updatedProduct.setCategory(product.getCategory());
        }
        if (product.getImageUrl() != null) {
            updatedProduct.setImageUrl(product.getImageUrl());
        }
        return convertProductToProductResponse(productRepository.save(updatedProduct));

    }

    @Override
    public ProductResponse replaceProduct(Long id, Product product) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            throw new ProductNotExistsException("Product with id " + id + " does not exist");
        }
        product.setId(id);
        return convertProductToProductResponse(productRepository.save(product));
    }

    @Override
    public boolean deleteProduct(Long id) {
        productRepository.deleteById(id);
        return true;
    }
}
