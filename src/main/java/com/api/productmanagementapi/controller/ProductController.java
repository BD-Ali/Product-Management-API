package com.api.productmanagementapi.controller;

import com.api.productmanagementapi.dtos.ProductCreate;
import com.api.productmanagementapi.dtos.ProductPatch;
import com.api.productmanagementapi.dtos.ProductUpdate;
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
    public GlobalResponse<Product> create(@Valid @RequestBody ProductCreate req) {
        Product toSave = req.toEntity();
        Product saved = service.create(toSave);

        return GlobalResponse.success(saved);
    }

    @PutMapping("/{id}")
    public GlobalResponse<Product> update(@PathVariable Long id,
                                          @Valid @RequestBody ProductUpdate req) {
        Product toUpdate = new Product();
        req.applyTo(toUpdate);
        Product updated = service.update(id, toUpdate);

        return GlobalResponse.success(updated);
    }

    @PatchMapping("/{id}")
    public GlobalResponse<Product> patch(@PathVariable Long id,
                                         @Valid @RequestBody ProductPatch req) {
        Product existing = service.get(id);
        req.applyPartially(existing);
        Product updated = service.update(id, existing);

        return GlobalResponse.success(updated);
    }

    @DeleteMapping("/{id}")
    public GlobalResponse<Map<String, String>> delete(@PathVariable Long id) {
        service.delete(id);

        return GlobalResponse.successMessage("Product deleted");
    }
}
