package com.valkyrie.cart_service.model;

public class Product {
    private int id;
    private String brand;
    private String name;
    private String description;
    private String specification;
    private String category;
    private int quantity;

    public int getId() {return id;}

    public String getBrand() {return brand;}

    public String getName() {return name;}

    public String getDescription() {return description;}

    public String getSpecification() {return specification;}

    public String getCategory() {return category;}

    public int getQuantity() {return quantity;}

    public Product setId(int id) {
        this.id = id;
        return this;
    }

    public Product setBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public Product setSpecification(String specification) {
        this.specification = specification;
        return this;
    }

    public Product setCategory(String category) {
        this.category = category;
        return this;
    }

    public Product setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", specification='" + specification + '\'' +
                ", category='" + category + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
