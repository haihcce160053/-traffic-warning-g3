package com.namhaigroup.map.object;

public class Orders {
    private int id;
    private String username;
    private int product_id;
    private int quantity;
    private double total_price;
    private String order_date;
    private int status;

    private Products products;

    private Orders_status orders_status;

    public Orders() {
    }

    public Orders(int id, String username, int product_id, int quantity, double total_price, String order_date, int status, Products products, Orders_status orders_status) {
        this.id = id;
        this.username = username;
        this.product_id = product_id;
        this.quantity = quantity;
        this.total_price = total_price;
        this.order_date = order_date;
        this.status = status;
        this.products = products;
        this.orders_status = orders_status;
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

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public Orders_status getOrders_status() {
        return orders_status;
    }

    public void setOrders_status(Orders_status orders_status) {
        this.orders_status = orders_status;
    }
}
