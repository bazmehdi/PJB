package com.bazmehdi.pjb.model;

import java.io.Serializable;

public class ItemModel implements Serializable{
    long id;
    int img;
    String name;
    long price;
    String category;
    String description;
    String ingredients;
    String recipes;
    int total=1;

    public ItemModel(long id, int img, String name, long price, String category, String description, String ingredients, String recipes) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.ingredients = ingredients;
        this.recipes = recipes;
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

    public String getDescription() {
        return description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getRecipes() {
        return recipes;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}