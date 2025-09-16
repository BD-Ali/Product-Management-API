package com.api.productmanagementapi.controller;

import com.api.productmanagementapi.entity.Product;
import com.api.productmanagementapi.service.ProductService;
import com.api.productmanagementapi.shared.GlobalResponse;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public GlobalResponse<List<Product>> all() {
        return GlobalResponse.success(service.all());
    }

    @GetMapping("/{id}")
    public GlobalResponse<Product> get(@PathVariable Long id) {
        return GlobalResponse.success(service.get(id));
    }

    @PostMapping
    public GlobalResponse<Product> create(@Valid @RequestBody Product body) {
        return GlobalResponse.success(service.create(body));
    }

    @PutMapping("/{id}")
    public GlobalResponse<Product> update(@PathVariable Long id, @Valid @RequestBody Product body) {
        return GlobalResponse.success(service.update(id, body));
    }

    @DeleteMapping("/{id}")
    public GlobalResponse<Map<String, String>> delete(@PathVariable Long id) {
        service.delete(id);
        return GlobalResponse.success(Map.of("message", "Product deleted"));
    }
}
