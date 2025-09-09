//package com.api.productmanagementapi.repository;
//
//import com.api.productmanagementapi.entity.Product;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//public interface ProductRepository extends JpaRepository<Product, Long> {}
//
//
package com.api.productmanagementapi.repository;

import com.api.productmanagementapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  }
