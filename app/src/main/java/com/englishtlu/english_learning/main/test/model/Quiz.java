package com.englishtlu.english_learning.main.test.model;

public class Quiz {
    int idQuiz;
    String nameQuiz;

    public Quiz(int idQuiz,String nameQuiz) {
        this.idQuiz = idQuiz;
        this.nameQuiz = nameQuiz;
    }
    public int getIdQuiz(){
        return idQuiz;
    }
    public String getNameQuiz() {
        return nameQuiz;
    }

    public void setNameQuiz(String nameQuiz) {
        this.nameQuiz = nameQuiz;
    }
}
