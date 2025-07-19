package com.valkyrie.order_service.model;

public class ProductWrapper {
    // private int id;
    private String brand;
    private String name;
    private String description;
    private String specification;

    // public int getId() {return id;}

    public String getBrand() {return brand;}

    public String getName() {return name;}

    public String getDescription() {return description;}

    public String getSpecification() {return specification;}

    public ProductWrapper setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public ProductWrapper setName(String name) {
        this.name = name;
        return this;
    }

    public ProductWrapper setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductWrapper setSpecification(String specification) {
        this.specification = specification;
        return this;
    }
}
