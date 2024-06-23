package com.englishtlu.english_learning.main.quizz.model;

import java.util.List;

public class QuestionModel {

 private int question_id;
    private String question;
    private int correctAnswer;
    private List<Answer> answers;

    private int selectedAnswer;

    public QuestionModel(int question_id, String question, int correctAnswer, List<Answer> answers,int selectedAnswer) {
        this.question_id = question_id;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.answers = answers;
        this.selectedAnswer = selectedAnswer;
    }

    public int getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(int selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
