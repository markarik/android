package com.example.ecommerceapp;

public class Product {

    private String category,name,price,strikedPrice;
    private int image;


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }


    public void setPrice(String price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getStrikedPrice() {
        return strikedPrice;
    }

    public void setStrikedPrice(String strikedPrice) {
        this.strikedPrice = strikedPrice;
    }
}
