package com.example.myfirebaseapp;

public class ReadWriteUserDetails {
    public String fullname, dob, gender, mobile;

    //Contructor
    public  ReadWriteUserDetails(){};

    public ReadWriteUserDetails(String textFullName, String textDob, String textGender, String textMobile){
        this.fullname = textFullName;
        this.dob = textDob;
        this.gender = textGender;
        this.mobile = textMobile;
    }

}
