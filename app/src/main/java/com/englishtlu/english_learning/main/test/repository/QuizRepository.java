package com.englishtlu.english_learning.main.test.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.englishtlu.english_learning.main.test.database.QuizDatabase;
import com.englishtlu.english_learning.main.test.model.Question;
import com.englishtlu.english_learning.main.test.model.Quiz;

import java.util.ArrayList;

public class QuizRepository {
    private Context context;
    private QuizDatabase dbHelper;
    public static int nuTrue;
    public static int nuWrong;
    public static int nuNoChoice;
    SQLiteDatabase myDB;
    private static ArrayList<Question> questionsData  = new ArrayList<>();
    private static ArrayList<Quiz> quizsData = new ArrayList<>();
    public ArrayList<Question> getQuizData() {
        return questionsData;
    }
    public QuizRepository(Context context){
        this.context = context;
        dbHelper = new QuizDatabase(context);
    }
    public QuizRepository(){

    }
    public ArrayList<Question> readQuiz(String quizID){
        questionsData = dbHelper.readQuiz(quizID);
        return questionsData;
    }
    public ArrayList<Quiz> readQuizList(){
        quizsData = dbHelper.readQuizList();
        Toast.makeText(this.context,quizsData.get(0).getNameQuiz().toString(),Toast.LENGTH_SHORT).show();
        return quizsData;
    }
    public void insertData(Quiz quiz){
        dbHelper.insertDataQuizs(quiz);
    }
}
