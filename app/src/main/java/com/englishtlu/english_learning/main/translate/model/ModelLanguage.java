package com.englishtlu.english_learning.main.translate.model;

public class ModelLanguage {
    private String languageCode;
    private String languageTitle;

    // Constructor
    public ModelLanguage(String languageCode, String languageTitle) {
        this.languageCode = languageCode;
        this.languageTitle = languageTitle;
    }

    // Getters and Setters
    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageTitle() {
        return languageTitle;
    }

    public void setLanguageTitle(String languageTitle) {
        this.languageTitle = languageTitle;
    }

    @Override
    public String toString() {
        return languageTitle; // This will display the language title in the Spinner
    }
}
