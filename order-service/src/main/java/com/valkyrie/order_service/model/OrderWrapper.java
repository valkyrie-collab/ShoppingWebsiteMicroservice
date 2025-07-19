package com.valkyrie.order_service.model;

public class OrderWrapper {
    private String id;
    private int quantity;
    private String address;
    private ProductWrapper product;
    // private String username;

    public String getId() {return id;}

    public int getQuantity() {return quantity;}

    public ProductWrapper getProducts() {return product;}

    public String getAddress() {return address;}

    // public String getUsername() {return username;}

    public OrderWrapper setId(String id) {
        this.id = id;
        return this;
    }

    public OrderWrapper setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderWrapper setProducts(ProductWrapper product) {
        this.product = product;
        return this;
    }

    public OrderWrapper setAddress(String address) {
        this.address = address;
        return this;
    }

    // public OrderWrapper setUsername(String username) {
    //     this.username = username;
    //     return this;
    // }

    @Override
    public String toString() {
        return id + quantity + product;
    }
}
