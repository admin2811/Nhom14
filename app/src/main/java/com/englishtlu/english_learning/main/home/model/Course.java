package com.englishtlu.english_learning.main.home.model;

public class Course {
    private  String picpath;
    private String title;
    private String description;
    private double star;

    private int id;
    public Course(int id, String picpath, String title, String description, double star) {
        this.id = id;
        this.picpath = picpath;
        this.title = title;
        this.description = description;
        this.star = star;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicpath() {
        return picpath;
    }


    public void setPicpath(String picpath) {
        this.picpath = picpath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }


}
