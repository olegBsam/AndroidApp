package com.example.anroid.DataBase.entities;

import android.graphics.Bitmap;

public class Note {
    private int id;
    private String text;
    private Bitmap image;

    public Note(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bitmap getImage() {
        return image;
    }

    public void addImage(Bitmap image){
        this.image = image;
    }
}
