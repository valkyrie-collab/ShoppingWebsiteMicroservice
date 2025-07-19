package com.valkyrie.cart_service.model;

import org.springframework.http.HttpStatus;

public class Store<Instance> {
    private final Instance instance;
    private final HttpStatus status;

    private Store(Instance instance, HttpStatus status) {
        this.instance = instance; this.status = status;
    }

    public static <Instance> Store<Instance> initialize(HttpStatus status, Instance instance) {
        return new Store<>(instance, status);
    }

    public Instance getInstance() {return instance;}

    public HttpStatus getStatus() {return status;}
}
