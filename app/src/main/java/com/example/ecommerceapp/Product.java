package com.example.ecommerceapp;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity public class Product {
    @Id Long id;
    private String category,name,price,strikedPrice,description,image;
//    private int image;

    public Product(String category, String name, String price, String description, String image) {
        this.category = category;
        this.name = name;
        this.price = price;
//        this.strikedPrice = strikedPrice;
        this.description = description;
        this.image = image;
    }

    public Product() {
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getStrikedPrice() {
        return strikedPrice;
    }

    public String getImage() {
        return image;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setStrikedPrice(String strikedPrice) {
        this.strikedPrice = strikedPrice;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}


