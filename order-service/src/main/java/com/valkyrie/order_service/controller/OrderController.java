package com.valkyrie.order_service.controller;

import java.util.List;

import com.valkyrie.order_service.config.TokenConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.valkyrie.order_service.model.Store;
import com.valkyrie.order_service.service.OrderService;
import com.valkyrie.order_service.model.OrderWrapper;

@RestController
@RequestMapping("/order")
public class OrderController {
    private TokenConfig config;
    @Autowired
    private void setConfig(TokenConfig config) {this.config = config;}

    private OrderService service;
    @Autowired
    private void setService(OrderService service) {this.service = service;}

    @PostMapping("/place-order")
    public ResponseEntity<String> placeOrder(@RequestParam int productId,
                                             @RequestParam int quantity, 
                                             @RequestParam String address,
                                             @RequestParam String token) {
        String username = config.getUsername(token);
        Store<String> store = service.saveOrder(productId, quantity, address, username);
        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @PostMapping("/update-order")
    public ResponseEntity<String> updateOrder(@RequestParam String uuid, 
                                              @RequestParam String address) {
        Store<String> store = service.updateOrder(uuid, address);
        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @DeleteMapping("/cancel-order")
    public ResponseEntity<String> cancelOrder(@RequestParam String uuid) {
        Store<String> store = service.cancelOrder(uuid);
        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }

    @GetMapping("/display-order")
    public ResponseEntity<List<OrderWrapper>> displayOrder(@RequestParam String token) {
        String username = config.getUsername(token);
        Store<List<OrderWrapper>> store = service.displayOrder(username);
        return ResponseEntity.status(store.getStatus()).body(store.getInstance());
    }
}
