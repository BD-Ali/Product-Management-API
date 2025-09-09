package com.api.productmanagementapi.controller;

import com.api.productmanagementapi.entity.Product;
import com.api.productmanagementapi.service.ProductService;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @org.springframework.web.bind.annotation.GetMapping
    public org.springframework.http.ResponseEntity<List<Product>> getAllProducts() {
        return new org.springframework.http.ResponseEntity<>(service.all(), org.springframework.http.HttpStatus.OK);
    }

    @org.springframework.web.bind.annotation.GetMapping("/{id}")
    public org.springframework.http.ResponseEntity<Product> getProductById(@org.springframework.web.bind.annotation.PathVariable Long id) {
        try {
            Product product = service.get(id);
            return new org.springframework.http.ResponseEntity<>(product, org.springframework.http.HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new org.springframework.http.ResponseEntity<>(null, org.springframework.http.HttpStatus.NOT_FOUND);
        }
    }

    @org.springframework.web.bind.annotation.PostMapping
    public org.springframework.http.ResponseEntity<Product> createProduct(@org.springframework.web.bind.annotation.RequestBody Product product) {
        Product created = service.create(product);
        return new org.springframework.http.ResponseEntity<>(created, org.springframework.http.HttpStatus.CREATED);
    }

    @org.springframework.web.bind.annotation.PutMapping("/{id}")
    public org.springframework.http.ResponseEntity<Product> updateProduct(@org.springframework.web.bind.annotation.PathVariable Long id, @org.springframework.web.bind.annotation.RequestBody Product product) {
        try {
            Product updated = service.update(id, product);
            return new org.springframework.http.ResponseEntity<>(updated, org.springframework.http.HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new org.springframework.http.ResponseEntity<>(null, org.springframework.http.HttpStatus.NOT_FOUND);
        }
    }

    @org.springframework.web.bind.annotation.DeleteMapping("/{id}")
    public org.springframework.http.ResponseEntity<Void> deleteProduct(@org.springframework.web.bind.annotation.PathVariable Long id) {
        try {
            service.delete(id);
            return new org.springframework.http.ResponseEntity<>(org.springframework.http.HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new org.springframework.http.ResponseEntity<>(org.springframework.http.HttpStatus.NOT_FOUND);
        }
    }
}
