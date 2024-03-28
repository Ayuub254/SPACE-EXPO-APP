package com.example.finalproject;

public class CardItem {
    private String title;
    private int imageResourceId;

    public CardItem(String title, int imageResourceId) {
        this.title = title;
        this.imageResourceId = imageResourceId;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
