package com.valkyrie.product_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.valkyrie.product_service.model.Product;
import com.valkyrie.product_service.model.Store;
import com.valkyrie.product_service.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService service;
    @Autowired
    private void setService(ProductService service) {this.service = service;}

    @PostMapping("/save-product")
    public ResponseEntity<String> saveProduct(@RequestBody Product product) {
        Store<String> store = service.saveProduct(product);
        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @PostMapping("/update-product")
    public ResponseEntity<String> updateProduct(@RequestParam int id, 
                                                 @RequestBody Product product) {
        Store<String> store = service.updateProduct(id, product);
        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @GetMapping("/find-all-product-by-name")
    public ResponseEntity<List<Product>> productsByName(@RequestParam String name) {
        Store<List<Product>> store = service.findAllProductByName(name);
        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @GetMapping("/find-all-product-by-category")
    public ResponseEntity<List<Product>> productsByCategory(@RequestParam String category) {
        Store<List<Product>> store = service.findAllProductByCategory(category);
        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @GetMapping("/find-all-product-by-brand")
    public ResponseEntity<List<Product>> productsByBrand(@RequestParam String brand) {
        Store<List<Product>> store = service.findAllProductsByBrand(brand);
        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @GetMapping("/find-product-by-id")
    public ResponseEntity<Product> productById(@RequestParam int id) {
        Store<Product> store = service.findProductById(id);
        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @DeleteMapping("/delete-product-by-id")
    public ResponseEntity<String> deleteById(@RequestParam int id) {
        Store<String> store = service.deleteProductById(id);
        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @DeleteMapping("/delete-product-by-brand")
    public ResponseEntity<String> deleteByBrand(@RequestParam String brand) {
        Store<String> store = service.deleteAllProductByBrand(brand);
        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @DeleteMapping("/delete-product-by-category")
    public ResponseEntity<String> deleteByCategory(@RequestParam String category) {
        Store<String> store = service.deleteAllProductByCategory(category);
        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @DeleteMapping("/delete-product-by-name")
    public ResponseEntity<String> deleteByName(@RequestParam String name) {
        Store<String> store = service.deleteAllProductByName(name);
        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }
}
