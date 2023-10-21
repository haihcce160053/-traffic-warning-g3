package com.namhaigroup.map.object;

public class Orders_status {
    private int id;
    private String name;

    public Orders_status() {
    }

    public Orders_status(int id, String name) {
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
