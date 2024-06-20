package com.englishtlu.english_learning.main.test.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.englishtlu.english_learning.main.test.model.Question;
import com.englishtlu.english_learning.main.test.model.Quiz;

import java.util.ArrayList;

public class QuizDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "QuizDatabase";
    private static final int DATABASE_VERSION = 1;
    public static final String TB_QUIZS = "Quizs";
    public static final String COL_QUIZID = "Id";
    public static final String COL_QUIZNAME = "QuizName";
    public static final String COL_QUESTIONID = "Id";
    public static final String TB_QUESTIONS = "Questions";
    public static final String COL_QUESTIONNAME = "Question";
    public static final String COL_QUESTIONTYPE = "Type";
    public static final String COL_ANSWER = "Answer";
    public static final String COL_IDQUIZ = "IdQuiz";
    public static final String COL_OPTIONA = "OptionA";
    public static final String COL_OPTIONB = "OptionB";
    public static final String COL_OPTIONC = "OptionC";
    public static final String COL_OPTIOND = "OptionD";
    public static final String COL_URL = "Url";
    private static ArrayList<Question> questionsData  = new ArrayList<>();
    private static ArrayList<Quiz> quizsData  = new ArrayList<>();
    SQLiteDatabase db;
    public QuizDatabase(@Nullable Context context){
        super(context,DB_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE " + TB_QUIZS + " (" + COL_QUIZID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_QUIZNAME + " TEXT); ";
        String query2 = "CREATE TABLE " + TB_QUESTIONS + " (" + COL_QUESTIONID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_QUESTIONNAME + " TEXT, " + COL_QUESTIONTYPE + " TEXT, "
                + COL_ANSWER + " TEXT, " + COL_IDQUIZ + " INTEGER, " + COL_OPTIONA + " TEXT, "
                + COL_OPTIONB + " TEXT, " + COL_OPTIONC + " TEXT, " + COL_OPTIOND + " TEXT, "
                + COL_URL + " TEXT DEFAULT NULL); ";
        db.execSQL(query1);
        db.execSQL(query2);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insertDataQuestions(Question question){
        db = this.getWritableDatabase();

        ContentValues questionValues = new ContentValues();
        questionValues.put(COL_QUESTIONNAME, question.getQuestion());
        questionValues.put(COL_QUESTIONTYPE, question.getQuestionType());
        questionValues.put(COL_ANSWER, question.getAnswer());
        questionValues.put(COL_IDQUIZ, question.getIdQuiz());
        questionValues.put(COL_OPTIONA, question.getOptionA());
        questionValues.put(COL_OPTIONB, question.getOptionB());
        questionValues.put(COL_OPTIONC, question.getOptionC());
        questionValues.put(COL_OPTIOND, question.getOptionD());
        questionValues.put(COL_URL, question.getUrlMedia());
        db.insert(TB_QUESTIONS, null, questionValues);
    }
    public void insertDataQuizs(Quiz quiz){
        db = this.getWritableDatabase();

        ContentValues quizValues = new ContentValues();
        quizValues.put(COL_QUIZNAME, quiz.getNameQuiz());
        db.insert(TB_QUIZS, null, quizValues);
    }
    public ArrayList<Question> readQuiz(String quizID){
        if(questionsData.size() != 0){
            questionsData.clear();
        }
        db = this.getReadableDatabase();

        String query = "SELECT * FROM " + QuizDatabase.TB_QUESTIONS + " WHERE "
                + QuizDatabase.COL_IDQUIZ + " = " + quizID;
        Cursor cursor = db.rawQuery(query,null);
        if (cursor != null && cursor.moveToFirst()) {
            int questionIndex = cursor.getColumnIndex(QuizDatabase.COL_QUESTIONNAME);
            int questiontypeIndex = cursor.getColumnIndex(QuizDatabase.COL_QUESTIONTYPE);
            int optionAIndex = cursor.getColumnIndex(QuizDatabase.COL_OPTIONA);
            int optionBIndex = cursor.getColumnIndex(QuizDatabase.COL_OPTIONB);
            int optionCIndex = cursor.getColumnIndex(QuizDatabase.COL_OPTIONC);
            int optionDIndex = cursor.getColumnIndex(QuizDatabase.COL_OPTIOND);
            int answerIndex = cursor.getColumnIndex(QuizDatabase.COL_ANSWER);
            int urlIndex = cursor.getColumnIndex(QuizDatabase.COL_URL);
            int quizIdIndex = cursor.getColumnIndex(QuizDatabase.COL_IDQUIZ);
            if (questionIndex != -1 && questiontypeIndex != -1 && optionAIndex != -1 && optionBIndex != -1 && optionCIndex != -1 && optionDIndex != -1 && answerIndex != -1 && urlIndex != -1 && quizIdIndex != -1)
                do {
                    String question = cursor.getString(questionIndex);
                    String questionType = cursor.getString(questiontypeIndex);
                    String optionA = cursor.getString(optionAIndex);
                    String optionB = cursor.getString(optionBIndex);
                    String optionC = cursor.getString(optionCIndex);
                    String optionD = cursor.getString(optionDIndex);
                    String answer = cursor.getString(answerIndex);
                    String url = cursor.getString(urlIndex);
                    int quizId = cursor.getInt(quizIdIndex);

                    Question questionObj = new Question(question,questionType,answer,quizID,optionA,optionB,optionC,optionD,url);
                    questionsData.add(questionObj);
                } while (cursor.moveToNext());
            cursor.close();
        }

        return questionsData;
    }
    public ArrayList<Quiz> readQuizList(){
        db = this.getReadableDatabase();

        String query = "SELECT * FROM " + QuizDatabase.TB_QUIZS;
        Cursor cursor = db.rawQuery(query,null);
        if (cursor != null && cursor.moveToFirst()){
            int quizIdIndex = cursor.getColumnIndex(QuizDatabase.COL_QUIZID);
            int quizNameIndex = cursor.getColumnIndex(QuizDatabase.COL_QUIZNAME);
            if(quizIdIndex != -1 && quizNameIndex != -1){
                do {
                    int quizID = cursor.getInt(quizIdIndex);
                    String quizName = cursor.getString(quizNameIndex);

                    Quiz quiz = new Quiz(quizID,quizName);
                    quizsData.add(quiz);

                } while (cursor.moveToNext());
                cursor.close();
            }
        }
        return quizsData;
    }
    public SQLiteDatabase open(){
        return this.getWritableDatabase();
    }
}
