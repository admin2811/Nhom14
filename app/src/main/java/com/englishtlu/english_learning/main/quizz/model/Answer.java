package com.englishtlu.english_learning.main.quizz.model;

public class Answer {
    private int answer_id;
    private String answer;

    public Answer(int answer_id, String answer) {
        this.answer_id = answer_id;
        this.answer = answer;
    }

    public int getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(int answer_id) {
        this.answer_id = answer_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isCorrect() {
        return true;
    }
}
