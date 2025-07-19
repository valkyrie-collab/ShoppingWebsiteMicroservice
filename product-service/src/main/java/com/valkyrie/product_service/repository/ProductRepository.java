package com.valkyrie.product_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.valkyrie.product_service.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByName(String name);

    List<Product> findAllByCategory(String category);

    List<Product> findAllByBrand(String brand);

    List<Product> deleteAllByName(String name);

    List<Product> deleteAllByCategory(String category);

    List<Product> deleteAllByBrand(String brand);
}
