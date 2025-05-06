package com.example.productservice.services;

import com.example.productservice.dtos.FakestoreProductDto;
import com.example.productservice.dtos.ProductResponse;
import com.example.productservice.exceptions.ProductNotExistsException;
import com.example.productservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
//@Primary
@Service("fakestoreProductService")
public class FakestoreProductService implements ProductService{
    private RestTemplate restTemplate;
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public FakestoreProductService(RestTemplate restTemplate, RedisTemplate redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    private ProductResponse convertFakeStoreProductToProductResponse(FakestoreProductDto productDto) {
        return new ProductResponse(productDto.getId(), productDto.getTitle(), productDto.getPrice(), productDto.getDescription(), productDto.getCategory(), productDto.getImage());

    }

    @Override
    public Page<ProductResponse> getAllProducts(int pageNumber, int pageSize, String sortBy, String sortDir) {
        List<FakestoreProductDto> productDtoList = List.of(restTemplate.getForObject("https://fakestoreapi.com/products", FakestoreProductDto[].class));
        return new PageImpl<>(productDtoList.stream().map(this::convertFakeStoreProductToProductResponse).toList());
    }

    @Override
    public ProductResponse getProductById(Long id) {
        // Check if the product is in Redis cache
        ProductResponse cachedProduct = (ProductResponse) redisTemplate.opsForHash().get("PRODUCTS", "PRODUCT_" + id);
        if (cachedProduct != null) {
            return cachedProduct;
        }
        // If not in cache, fetch from the external API
        // and store it in Redis cache
        FakestoreProductDto productDto = restTemplate.getForObject("https://fakestoreapi.com/products/" + id, FakestoreProductDto.class);
        if (productDto == null) {
            throw new ProductNotExistsException("Product with id " + id + " does not exist");
        }
        // Store the product in Redis cache
        ProductResponse product = convertFakeStoreProductToProductResponse(productDto);
        redisTemplate.opsForHash().put("PRODUCTS", "PRODUCT_" + id, product);
        return product;
    }

    @Override
    public ProductResponse saveProduct(Product product) {
        FakestoreProductDto productDto = restTemplate.postForObject("https://fakestoreapi.com/products", product, FakestoreProductDto.class);
        return convertFakeStoreProductToProductResponse(productDto);
    }

    @Override
    public ProductResponse updateProduct(Long id, Product product) {
        FakestoreProductDto productDto = restTemplate.patchForObject("https://fakestoreapi.com/products/" + product.getId(), product, FakestoreProductDto.class);
        return convertFakeStoreProductToProductResponse(productDto);
    }

    @Override
    public ProductResponse replaceProduct(Long id, Product product) {
        FakestoreProductDto productDto = restTemplate.exchange("https://fakestoreapi.com/products/" + product.getId(), HttpMethod.PUT, new HttpEntity<>(product), FakestoreProductDto.class).getBody();
        return convertFakeStoreProductToProductResponse(productDto);
    }

    @Override
    public boolean deleteProduct(Long id) {
        restTemplate.delete("https://fakestoreapi.com/products/" + id);
        return true;
    }

}
