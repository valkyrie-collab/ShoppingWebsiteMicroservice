package com.valkyrie.cart_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.valkyrie.cart_service.model.Store;
import com.valkyrie.cart_service.service.CartService;
import com.valkyrie.cart_service.model.CartWrapper;

@RestController
@RequestMapping("/cart")
public class CartController {
    private CartService service;
    @Autowired
    private void setService(CartService service) {this.service = service;}

    @PostMapping("/save-to-cart")
    public ResponseEntity<String> saveCart(@RequestParam int productId, 
                                           @RequestParam String username,
                                           @RequestParam int quantity) {
        Store<String> store = service.saveCart(productId, username, quantity);
        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @PostMapping("/update-cart")
    public ResponseEntity<String> updateCart(@RequestParam int quantity, @RequestParam int id) {
        Store<String> store = service.updateCart(quantity, id);
        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @GetMapping("/display-cart")
    public ResponseEntity<List<CartWrapper>> displayCart(@RequestParam String username) {
        Store<List<CartWrapper>> store = service.displayCart(username);
        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }
}
