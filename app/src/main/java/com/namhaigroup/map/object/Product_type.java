package com.namhaigroup.map.object;

public class Product_type {
    private int id;
    private String name;

    public Product_type() {
    }

    public Product_type(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
