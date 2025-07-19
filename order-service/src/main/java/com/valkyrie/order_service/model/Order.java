package com.valkyrie.order_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    private String id;
    private int quantity;
    private String address;
    private int productId;
    private String username;

    public String getId() {return id;}

    public int getQuantity() {return quantity;}

    public int getProducts() {return productId;}

    public String getAddress() {return address;}

    public String getUsername() {return username;}

    public Order setId(String id) {
        this.id = id;
        return this;
    }

    public Order setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public Order setProducts(int productId) {
        this.productId = productId;
        return this;
    }

    public Order setAddress(String address) {
        this.address = address;
        return this;
    }

    public Order setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public String toString() {
        return id + quantity + productId + username + address;
    }
}
