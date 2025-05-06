package com.example.productservice.controllers;

import com.example.productservice.commons.AuthenticationCommons;
import com.example.productservice.dtos.ProductResponse;
import com.example.productservice.dtos.UserDto;
import com.example.productservice.models.Product;
import com.example.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    private RestTemplate restTemplate;
    private AuthenticationCommons authenticationCommons;

    @Autowired
    public ProductController(@Qualifier("selfProductService") ProductService productService, RestTemplate restTemplate, AuthenticationCommons authenticationCommons) {
        this.productService = productService;
        this.restTemplate = restTemplate;
        this.authenticationCommons = authenticationCommons;
    }

    @GetMapping()
    public ResponseEntity<Page<ProductResponse>> getAllProducts(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize, @RequestParam("sortBy") String sortBy, @RequestParam("sortDir") String sortDir) {
        // Uncomment the following lines to enable authentication
//        ResponseEntity<List<Product>> response = new ResponseEntity<>(productService.getAllProducts(), null, HttpStatus.OK);
//        UserDto userDto = authenticationCommons.validateToken(token);
//        if (userDto == null) {
//            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
//        }
//        if (userDto.getRoles() == null || !userDto.getRoles().contains("ADMIN")) {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
        return ResponseEntity.ok(productService.getAllProducts(pageNum, pageSize, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping()
    public ResponseEntity<ProductResponse> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.saveProduct(product));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> replaceProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.replaceProduct(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
