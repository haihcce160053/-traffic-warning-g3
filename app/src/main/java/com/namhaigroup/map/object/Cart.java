package com.namhaigroup.map.object;

public class Cart {
    private int id;
    private String username;
    private int product_id;
    private int quantity;
    private Products products;

    public Cart() {
    }

    public Cart(int id, String username, int product_id, int quantity, Products products) {
        this.id = id;
        this.username = username;
        this.product_id = product_id;
        this.quantity = quantity;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }
}
