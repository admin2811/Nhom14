package com.englishtlu.english_learning.main.quizz.model;
import java.io.Serializable;
public class CheckModel implements Serializable  {
    private int id;
    private String Question;
    private String CorrectAnswer;

    public CheckModel(int id, String question, String correctAnswer) {
        this.id = id;
        Question = question;
        CorrectAnswer = correctAnswer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getCorrectAnswer() {
        return CorrectAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        CorrectAnswer = correctAnswer;
    }
}
