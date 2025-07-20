package com.valkyrie.cart_service.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valkyrie.cart_service.feign.ProductFeignController;
import com.valkyrie.cart_service.model.Cart;
import com.valkyrie.cart_service.model.Product;
import com.valkyrie.cart_service.model.Store;
import com.valkyrie.cart_service.repository.CartRepository;
import com.valkyrie.cart_service.model.CartWrapper;

@Service
public class CartService {
    private CartRepository repo;
    @Autowired
    private void setRepo(CartRepository repo) {this.repo = repo;}

    private ProductFeignController feign;
    @Autowired
    private void setFeign(ProductFeignController feign) {this.feign = feign;}

    public Store<String> saveCart(int productId, String username, int quantity) {
        Product product = feign.productById(productId).getBody();

        if (product == null) {
            return Store.initialize(
                HttpStatus.BAD_REQUEST, "Product not saved in the cart"
            );
        }

        if (product.getQuantity() < quantity) {
            return Store.initialize(
                HttpStatus.NOT_ACCEPTABLE, "Quantity of items in the cart exceed the product quantity"
            );
        }

        Cart cart = new Cart().setBrand(product.getBrand()).setName(product.getName()).setUsername(username)
                        .setDescription(product.getDescription()).setSpecification(product.getSpecification())
                        .setQuantity(quantity).setProductId(productId);
        repo.save(cart);

        product = product.setQuantity(product.getQuantity() - quantity);
        feign.updateProduct(productId, product);

        return repo.findById(cart.getId()).orElse(null) == null?
                    Store.initialize(HttpStatus.BAD_REQUEST, "Product not Saved...") :
                    Store.initialize(HttpStatus.ACCEPTED, "Product saved successfully...");
    }

    public Store<String> updateCart(int quantity, int id) {
        Cart cart = repo.findById(id).orElse(null);

        if (cart == null) {
            return Store.initialize(HttpStatus.NOT_ACCEPTABLE, "Cart is empty...");
        }

        Product product = feign.productById(cart.getProductId()).getBody();

        if (product == null) {
            return Store.initialize(
                HttpStatus.BAD_REQUEST, "Cart not updated in the cart"
            );
        }

        product = product.setQuantity(product.getQuantity() - quantity);
        cart = cart.setQuantity(cart.getQuantity() + quantity);

        feign.updateProduct(cart.getProductId(), product);
        repo.save(cart);

        cart = repo.findById(cart.getId()).orElse(null);
        return cart != null?Store.initialize(HttpStatus.ACCEPTED, 
                    "Product with ID = " + product.getId() + " has been successfully updated in cart") :
                    Store.initialize(HttpStatus.NOT_ACCEPTABLE, "Product not update in cart...");
    }

    public Store<List<CartWrapper>> displayCart(String username) {
        List<Cart> carts = repo.findAllByUsername(username);
        List<CartWrapper> wrappers = new ArrayList<>();
        
        if (carts.isEmpty()) {
            return Store.initialize(HttpStatus.BAD_REQUEST, new ArrayList<>());
        }

        for (Cart cart : carts) {
            wrappers.add(
                new CartWrapper().setBrand(cart.getBrand()).setDescription(cart.getDescription())
                                .setId(cart.getId()).setName(cart.getName()).setProductId(cart.getProductId())
                                .setQuantity(cart.getQuantity()).setSpecification(cart.getSpecification())
            );
        }

        return Store.initialize(HttpStatus.OK, wrappers);
    }

    public Store<String> removeFromCart(int id) {
        repo.deleteById(id);
        return repo.findById(id).orElse(null) == null?
                Store.initialize(HttpStatus.OK, "removed successfully...") : 
                Store.initialize(HttpStatus.BAD_REQUEST, "Remove is not successfull....");
    }

    @Transactional
    public Store<String> removeFromCart(String username) {
        repo.deleteAllByUsername(username);
        return repo.findAllByUsername(username).isEmpty()?
                Store.initialize(HttpStatus.OK, "removed successfully...") : 
                Store.initialize(HttpStatus.BAD_REQUEST, "Remove is not successfull....");
    }
}
