package com.namhaigroup.map.object;

public class OrderHistory {
    private int id;
    private String username;
    private String product_name;
    private String order_date;
    private double order_price;
    private int product_id;

    public OrderHistory() {
    }

    public OrderHistory(int id, String username, String product_name, String order_date, double order_price, int product_id) {
        this.id = id;
        this.username = username;
        this.product_name = product_name;
        this.order_date = order_date;
        this.order_price = order_price;
        this.product_id = product_id;
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

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public double getOrder_price() {
        return order_price;
    }

    public void setOrder_price(double order_price) {
        this.order_price = order_price;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}
