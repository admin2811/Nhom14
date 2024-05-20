package com.englishtlu.english_learning.main.profile.model;

public class Profile {
    private String fullname;
    private String mobile;
    private String dob;
    private String gender;
    private String school;
    private String relative;
    private String profileDocId;
    private String currentUserId;

    private String profileImage;

    public  Profile(){}


    public Profile(String fullname, String mobile, String dob, String gender, String school, String relative, String profileDocId, String currentUserId, String profileImage) {
        this.fullname = fullname;
        this.mobile = mobile;
        this.dob = dob;
        this.gender = gender;
        this.school = school;
        this.relative = relative;
        this.profileDocId = profileDocId;
        this.currentUserId = currentUserId;
        this.profileImage = profileImage;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getRelative() {
        return relative;
    }

    public void setRelative(String relative) {
        this.relative = relative;
    }

    public String getProfileDocId() {
        return profileDocId;
    }

    public void setProfileDocId(String profileDocId) {
        this.profileDocId = profileDocId;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }
}
