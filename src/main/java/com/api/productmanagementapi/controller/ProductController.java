package com.api.productmanagementapi.controller;

import com.api.productmanagementapi.entity.Product;
import com.api.productmanagementapi.service.ProductService;
import com.api.productmanagementapi.shared.GlobalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<GlobalResponse<List<Product>>> getAllProducts() {
        return ResponseEntity.ok(GlobalResponse.success(service.all()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse<Product>> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(GlobalResponse.success(service.get(id)));
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<GlobalResponse<Product>> createProduct(@Valid @RequestBody Product product) {
        Product created = service.create(product);
        URI location = URI.create("/api/products/" + created.getId());
        return ResponseEntity
                .created(location)
                .body(GlobalResponse.success(created));
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<GlobalResponse<Product>> updateProduct(@PathVariable Long id,
                                                                 @RequestBody Product product) {
        Product updated = service.update(id, product);
        return ResponseEntity.ok(GlobalResponse.success(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GlobalResponse<Void>> deleteProduct(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(GlobalResponse.message("Product deleted"));
    }
}
