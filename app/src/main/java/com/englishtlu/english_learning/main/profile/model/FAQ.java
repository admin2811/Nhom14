package com.englishtlu.english_learning.main.profile.model;

public class FAQ {
    private String question, answer;
    private boolean expandable;

    public FAQ() {
        // Empty constructor needed for Firebase
    }

    public FAQ(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.expandable = false;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
