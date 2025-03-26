package com.example.productservice.services;

import com.example.productservice.dtos.FakestoreProductDto;
import com.example.productservice.exceptions.ProductNotExistsException;
import com.example.productservice.models.Category;
import com.example.productservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Service("fakestoreProductService")
public class FakestoreProductService implements ProductService{
    private RestTemplate restTemplate;

    private Product convertFakeStoreProductToProduct(FakestoreProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(new Category());
        product.getCategory().setName(productDto.getCategory());
        product.setImageUrl(productDto.getImage());
        return product;
    }

    @Autowired
    public FakestoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Product> getAllProducts() {
        List<FakestoreProductDto> productDtoList = List.of(restTemplate.getForObject("https://fakestoreapi.com/products", FakestoreProductDto[].class));
        return productDtoList.stream().map(this::convertFakeStoreProductToProduct).toList();
    }

    @Override
    public Product getProductById(Long id) {
        FakestoreProductDto productDto = restTemplate.getForObject("https://fakestoreapi.com/products/" + id, FakestoreProductDto.class);
        if (productDto == null) {
            throw new ProductNotExistsException("Product with id " + id + " does not exist");
        }
        return convertFakeStoreProductToProduct(productDto);
    }

    @Override
    public Product saveProduct(Product product) {
        FakestoreProductDto productDto = restTemplate.postForObject("https://fakestoreapi.com/products", product, FakestoreProductDto.class);
        return convertFakeStoreProductToProduct(productDto);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        FakestoreProductDto productDto = restTemplate.patchForObject("https://fakestoreapi.com/products/" + product.getId(), product, FakestoreProductDto.class);
        return convertFakeStoreProductToProduct(productDto);
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        FakestoreProductDto productDto = restTemplate.exchange("https://fakestoreapi.com/products/" + product.getId(), HttpMethod.PUT, new HttpEntity<>(product), FakestoreProductDto.class).getBody();
        return convertFakeStoreProductToProduct(productDto);
    }

    @Override
    public boolean deleteProduct(Long id) {
        restTemplate.delete("https://fakestoreapi.com/products/" + id);
        return true;
    }

}
