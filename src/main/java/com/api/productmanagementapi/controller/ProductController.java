package com.api.productmanagementapi.controller;

import com.api.productmanagementapi.entity.Product;
//import com.api.productmanagementapi.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final List<Product> products=new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        for(Product p:products){
            if(p.getId().equals(id)){
                return new ResponseEntity<>(p,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        products.add(product);
        return new ResponseEntity<>(product,HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,@RequestBody Product updatedProduct){
        for(Product p:products){
            if(p.getId().equals(id)){
                p.setName(updatedProduct.getName());
                p.setPrice(updatedProduct.getPrice());
                p.setQuantity(updatedProduct.getQuantity());
                return new ResponseEntity<>(p,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        for(Product p:products){
            if(p.getId().equals(id)){
                products.remove(p);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
//    private final ProductService service;
//
//    public ProductController(ProductService service) { this.service = service; }
//
//    @GetMapping
//    public List<Product> all() { return service.all(); }
//
//    @GetMapping("/{id}")
//    public Product get(@PathVariable Long id) { return service.get(id); }
//
//    @PostMapping
//    public ResponseEntity<Product> create(@RequestBody Product p) {
//        Product created = service.create(p);
//        return ResponseEntity.status(HttpStatus.CREATED).body(created);
//    }
//
//    @PutMapping("/{id}")
//    public Product update(@PathVariable Long id, @RequestBody Product p) {
//        return service.update(id, p);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        service.delete(id);
//        return ResponseEntity.noContent().build();
//    }
}
