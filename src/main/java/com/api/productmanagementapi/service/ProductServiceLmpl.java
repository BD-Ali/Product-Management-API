//package com.api.productmanagementapi.service;
//
//import com.api.productmanagementapi.entity.Product;
//import com.api.productmanagementapi.repository.ProductRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@Transactional
//public class ProductServiceLmpl implements ProductService {
//    private final ProductRepository repo;
//
//    public ProductServiceLmpl(ProductRepository repo) {
//        this.repo = repo;
//    }
//
//    @Override
//    public List<Product> all() {
//        return repo.findAll();
//    }
//
//    @Override
//    public Product get(Long id) {
//        return repo.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Product " + id + " not found"));
//        // (optional) create a custom ProductNotFoundException instead of IllegalArgumentException
//    }
//
//    @Override
//    public Product create(Product p) {
//        return repo.save(p);
//    }
//
//    @Override
//    public Product update(Long id, Product p) {
//        Product existing = get(id);
//
//        if (p.getName() != null)     existing.setName(p.getName());
//        if (p.getPrice() != null)    existing.setPrice(p.getPrice());
//        if (p.getQuantity() != null) existing.setQuantity(p.getQuantity());
//
//        return repo.save(existing);
//    }
//
//    @Override
//    public void delete(Long id) {
//        if (!repo.existsById(id)) {
//            throw new IllegalArgumentException("Product " + id + " not found");
//        }
//        repo.deleteById(id);
//    }
//}
