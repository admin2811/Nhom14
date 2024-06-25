package com.englishtlu.english_learning.main.quizz.model;

public class LessonVocab {
    private  String picpath;
    private String title;
    private String description;

    private int id;

    public LessonVocab(int id, String picpath, String title, String description) {
        this.id = id;
        this.picpath = picpath;
        this.title = title;
        this.description = description;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
