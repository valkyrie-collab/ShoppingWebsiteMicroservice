package com.valkyrie.product_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valkyrie.product_service.model.Product;
import com.valkyrie.product_service.model.Store;
import com.valkyrie.product_service.repository.ProductRepository;

@Service
public class ProductService {
    private static final Product defaultProduct = new Product().setId(-1).setName("null").setBrand("null")
            .setCategory("null").setDescription("null").setSpecification("null")
            .setQuantity(-1);
    private ProductRepository repo;
    @Autowired
    private void setRepo(ProductRepository repo) {this.repo = repo;}

    public Store<String> saveProduct(Product product) {
        repo.save(product);
        return repo.findById(product.getId()).orElse(null) == null?
            Store.initialize(HttpStatus.BAD_REQUEST, "Product is not saved") : 
            Store.initialize(HttpStatus.ACCEPTED, "Product saved successfully with ID = " + product.getId());
    }

    public Store<String> updateProduct(int id, Product product) {
        product = product.setId(id);
        Product presentProduct = repo.findById(id).orElse(null);

        if (presentProduct == null) {
            repo.save(product);
            return repo.findById(id).orElse(null) == null?
                Store.initialize(HttpStatus.BAD_REQUEST, "Problem happed during product saving...") : 
                Store.initialize(HttpStatus.ACCEPTED, "Product saved successfully.....");
        }else if (!product.toString().equals(presentProduct.toString())) {
            repo.save(product);
            Product checkProduct = repo.findById(id).orElse(null);
            return checkProduct != null && !checkProduct.equals(product)?
                Store.initialize(HttpStatus.OK, "Product has been added successfully.....") : 
                Store.initialize(HttpStatus.BAD_REQUEST, "Product not updated");
        }

        return Store.initialize(HttpStatus.BAD_REQUEST, "Operation update product is not possible...");
    }

    public Store<List<Product>> findAllProductByName(String name) {
        List<Product> products = repo.findAllByName(name);

        if (products.isEmpty()) {
            return Store.initialize(HttpStatus.BAD_REQUEST, List.of(defaultProduct));
        }

        return Store.initialize(HttpStatus.OK, products);
    }

    public Store<List<Product>> findAllProductByCategory(String category) {
        List<Product> products = repo.findAllByCategory(category);

        return products.isEmpty()? Store.initialize(HttpStatus.BAD_REQUEST, List.of(defaultProduct)) : 
                    Store.initialize(HttpStatus.OK, products);
    }

    public Store<List<Product>> findAllProductsByBrand(String brand) {
        List<Product> products = repo.findAllByBrand(brand);

        return products.isEmpty()? Store.initialize(HttpStatus.BAD_REQUEST, List.of(defaultProduct)) : 
                    Store.initialize(HttpStatus.OK, products);
    }

    public Store<Product> findProductById(int id) {
        Product product = repo.findById(id).orElse(null);

        return product == null? Store.initialize(HttpStatus.BAD_REQUEST, defaultProduct) : 
                                Store.initialize(HttpStatus.OK, product);
    }

    @Transactional
    public Store<String> deleteAllProductByName(String name) {

        if (repo.findAllByName(name).isEmpty()) {
            return Store.initialize(HttpStatus.OK, "There is nothing to delete..");
        }

        repo.deleteAllByName(name);

        return repo.findAllByName(name).isEmpty()? 
                Store.initialize(HttpStatus.ACCEPTED, 
                    "Deletion of all products with name = " + name + " is successful..") :
                Store.initialize(HttpStatus.BAD_REQUEST, "Deletion is not possible...");
    }

    @Transactional
    public Store<String> deleteAllProductByCategory(String category) {
        
        if (repo.findAllByCategory(category).isEmpty()) {
            return Store.initialize(HttpStatus.OK, "Three is nothing to delete...");
        }

        repo.deleteAllByCategory(category);

        return repo.findAllByCategory(category).isEmpty()? 
                Store.initialize(HttpStatus.ACCEPTED, 
                    "Deletion of all products with category = " + category + " is successful..") :
                Store.initialize(HttpStatus.BAD_REQUEST, "Deletion is not possible...");
    }

    @Transactional
    public Store<String> deleteAllProductByBrand(String brand) {

        if (repo.findAllByBrand(brand).isEmpty()) {
            return Store.initialize(HttpStatus.OK, "There is nothing to delete...");
        }

        repo.deleteAllByBrand(brand);

        return repo.findAllByBrand(brand).isEmpty()? 
                Store.initialize(HttpStatus.ACCEPTED, 
                    "Deletion of all products with Brand = " + brand + " is successful..") :
                Store.initialize(HttpStatus.BAD_REQUEST, "Deletion is not possible...");

    }

    public Store<String> deleteProductById(int id) {

        if (repo.findById(id).orElse(null) == null) {
            return Store.initialize(HttpStatus.OK, "The Product with Id = " + id + " is already deleted");
        }
        
        repo.deleteById(id);

        return repo.findById(id).orElse(null) == null? 
                Store.initialize(HttpStatus.OK, "The Product with ID = " + id + " has been deleted") :
                Store.initialize(HttpStatus.BAD_REQUEST, "Unable to delete the Product with ID = " + id);
    }


}
