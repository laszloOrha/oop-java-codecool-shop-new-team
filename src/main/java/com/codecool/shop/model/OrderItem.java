package com.codecool.shop.model;

public class OrderItem {
    private int id;
    private String name;
    private int quantity;
    public static int totalItems = 0;

    public OrderItem(int id, String name, int quantity) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
        totalItems ++;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getId() {
        return id;
    }

    public void increaseQuantity() {
        this.quantity ++;
        totalItems ++;
    }
}