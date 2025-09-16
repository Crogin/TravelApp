package com.example.travelplanapp.Model;

public class CategoryModel {
    private int id;
    private String name;
    private String imagePath;

    public CategoryModel() {
        // 无参构造方法（必须要有）
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}