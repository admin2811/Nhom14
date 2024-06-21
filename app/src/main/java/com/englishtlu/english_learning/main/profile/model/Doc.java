package com.englishtlu.english_learning.main.profile.model;

public class Doc {
    private String id;
    private String name;
    private String pdf;

    public Doc(String id, String name, String pdf) {
        this.id = id;
        this.name = name;
        this.pdf = pdf;
    }

    public Doc() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }
}
