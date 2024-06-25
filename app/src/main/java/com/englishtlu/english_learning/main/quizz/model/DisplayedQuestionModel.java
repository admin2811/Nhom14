package com.englishtlu.english_learning.main.quizz.model;

public class DisplayedQuestionModel {
    private String question;
    private String correctAnswer;
    private String selectedAnswer;

    public DisplayedQuestionModel(String question, String correctAnswer, String selectedAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.selectedAnswer = selectedAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }
}
