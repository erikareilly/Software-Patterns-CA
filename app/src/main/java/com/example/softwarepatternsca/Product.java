package com.example.softwarepatternsca;

public class Product {

    public String title, manufacturer, category;
    public double price;

    public Product(){

    }

    public Product(String title, String manufacturer,String category, double price){
        this.title=title;
        this.manufacturer=manufacturer;
        this.category=category;
        this.price=price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
