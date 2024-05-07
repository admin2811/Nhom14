package com.example.myfirebaseapp;

public class ReadWriteUserDetails {


    private String textFirstName;
    private String textLastName;
    private String textEmail;
    private String textPassword;
    private String textConfirmPassword;


    //Contructor
    public  ReadWriteUserDetails(){};

    //Getters and Setters
    public String getTextFirstName() {
        return textFirstName;
    }

    public void setTextFirstName(String textFirstName) {
        this.textFirstName = textFirstName;
    }

    public String getTextLastName() {
        return textLastName;
    }

    public void setTextLastName(String textLastName) {
        this.textLastName = textLastName;
    }

    public String getTextEmail() {
        return textEmail;
    }

    public void setTextEmail(String textEmail) {
        this.textEmail = textEmail;
    }

    public String getTextPassword() {
        return textPassword;
    }

    public void setTextPassword(String textPassword) {
        this.textPassword = textPassword;
    }

    public String getTextConfirmPassword() {
        return textConfirmPassword;
    }

    public void setTextConfirmPassword(String textConfirmPassword) {
        this.textConfirmPassword = textConfirmPassword;
    }

    public ReadWriteUserDetails(String textFirstName, String textLastName) {
        this.textFirstName = textFirstName;
        this.textLastName = textLastName;
        this.textEmail = textEmail;
        this.textPassword = textPassword;
        this.textConfirmPassword = textConfirmPassword;
    }
}
