package com.bazmehdi.pjb.model;

public class ViewModel {
    private String text;
    private int image;

    public ViewModel(String text, int image) {
        this.text = text;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public int getImage() {
        return image;
    }
}

