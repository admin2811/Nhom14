package com.englishtlu.english_learning.main.analysis.model;

public class Progresses {
    String nameTest;
    String numtrue;
    int lenQuiz;

    public Progresses(String nameTest, String numtrue,int lenQuiz) {
        this.nameTest = nameTest;
        this.numtrue = numtrue;
        this.lenQuiz = lenQuiz;
    }

    public String getNameTest() {
        return nameTest;
    }

    public void setNameTest(String nameTest) {
        this.nameTest = nameTest;
    }

    public String getNumtrue() {
        return numtrue;
    }

    public void setNumtrue(String numtrue) {
        this.numtrue = numtrue;
    }
    public int getLenQuiz() {
        return lenQuiz;
    }

    public void setLenQuiz(int lenQuiz) {
        this.lenQuiz = lenQuiz;
    }
}
