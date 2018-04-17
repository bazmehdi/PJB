package com.bazmehdi.pjb.model;

import java.io.Serializable;

public class ItemModel implements Serializable{
    long id;
    int img;
    String name;
    long price;
    String category;
    int total=1;

    public ItemModel(){

    }

    public ItemModel(long id, int img, String name, long price, String category) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public int getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public String getStrPrice() {
        return "£ " + getPrice();
    }

    public long getPrice() {
        return (price);
    }

    public long getSumPrice() {
        return (price*total);
    }

    public String getCategory() {
        return category;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}