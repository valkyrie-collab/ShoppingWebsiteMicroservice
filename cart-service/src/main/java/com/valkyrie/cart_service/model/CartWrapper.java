package com.valkyrie.cart_service.model;

public class CartWrapper {
    private int id;//hidden
    private int productId;//hidden
    private String brand;
    private String name;
    private String description;
    private String specification;
    private int quantity;

    public int getId() {return id;}

    public int getProductId() {return productId;}

    public String getBrand() {return brand;}

    public String getName() {return name;}

    public int getQuantity() {return quantity;}

    public String getDescription() {return description;}

    public String getSpecification() {return specification;}

     public CartWrapper setId(int id) {
        this.id = id;
        return this;
    }

    public CartWrapper setProductId(int productId) {
        this.productId = productId;
        return this;
    }

    public CartWrapper setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public CartWrapper setName(String name) {
        this.name = name;
        return this;
    }

    public CartWrapper setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public CartWrapper setDescription(String description) {
        this.description = description;
        return this;
    }

    public CartWrapper setSpecification(String specification) {
        this.specification = specification;
        return this;
    }
}
