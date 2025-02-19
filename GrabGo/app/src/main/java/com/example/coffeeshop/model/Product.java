package com.example.coffeeshop.model;

public class Product {
    private String name, category, imageUrl;
    private double price;

    public Product() {
        // Empty constructor required for Firebase
    }

    public Product(String name, String category, double price, String imageUrl) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public String getImageUrl() { return imageUrl; }
}
