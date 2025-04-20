package com.example.caraccessories;

public class Accessories {
    private String name;
    private double price;
    private int stock;
    private String imageName;

    public Accessories(String name, double price, int stock, String imageName) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imageName = imageName;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }
    public void  setStock(int stock){
        this.stock = stock;
    }
    public String getImageName() {
        return imageName;
    }
}