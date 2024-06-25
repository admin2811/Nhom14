package com.englishtlu.english_learning.main.quizz.model;

public class QuestionCorrectAwnser {
    private int id;
    private String question;
    private int correct_answer;
    private String category_name;

    public QuestionCorrectAwnser(int id, String question, int correct_answer, String category_name) {
        this.id = id;
        this.question = question;
        this.correct_answer = correct_answer;
        this.category_name = category_name;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getCorrectAnswer() {
        return correct_answer;
    }

    public void setCorrectAnswer(int correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String getCategoryName() {
        return category_name;
    }

    public void setCategoryName(String category_name) {
        this.category_name = category_name;
    }
}
