package com.api.productmanagementapi.service;

import com.api.productmanagementapi.entity.Product;
import com.api.productmanagementapi.repository.ProductRepository;
import com.api.productmanagementapi.shared.CustomResponseException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceLmpl implements ProductService {
    private final ProductRepository repo;

    public ProductServiceLmpl(@Qualifier("productRepository") ProductRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Product> all() {
        return repo.findAll();
    }

    @Override
    public Product get(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new CustomResponseException(
                        404,"Product with id " + id + " not found"));}

    @Override
    public Product create(Product p) {
        if (p.getPrice() != null && p.getPrice().doubleValue() < 0) {
            throw new CustomResponseException(HttpStatus.BAD_REQUEST.value(),"Price cannot be negative");
        }
        if (p.getQuantity() != null && p.getQuantity() < 0) {
            throw new CustomResponseException(HttpStatus.BAD_REQUEST.value(),"Quantity cannot be negative");
        }
        return repo.save(p);
    }

    @Override
    public Product update(Long id, Product p) {
        Product existing = get(id);

        if (p.getName() != null) {
            existing.setName(p.getName());
        }
        if (p.getPrice() != null) {
            if (p.getPrice().doubleValue() < 0) {
                throw new CustomResponseException(HttpStatus.BAD_REQUEST.value(),"Price cannot be negative");
            }
            existing.setPrice(p.getPrice());
        }
        if (p.getQuantity() != null) {
            if (p.getQuantity() < 0) {
                throw new CustomResponseException(HttpStatus.BAD_REQUEST.value(),"Quantity cannot be negative");
            }
            existing.setQuantity(p.getQuantity());
        }

        return repo.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new CustomResponseException(HttpStatus.NOT_FOUND.value(),"Product with id " + id + " not found");
        }
        repo.deleteById(id);
    }
}
