package com.englishtlu.english_learning.main.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.test.database.QuizDatabase;
import com.englishtlu.english_learning.main.test.model.Question;

public class QuizStartActivity extends AppCompatActivity {
    private int lenQuiz;
    private int idQuiz;
    private TextView txtlenQuiz;
    private Button btnstartQuiz;
    QuizDatabase dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz_start);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // dbHelper = new QuizDatabase(this);

        txtlenQuiz = findViewById(R.id.lenQuiz);
        btnstartQuiz = findViewById(R.id.btnstartQuiz);

        lenQuiz = getIntent().getIntExtra("lenQuiz",0);
        idQuiz = getIntent().getIntExtra("idQuiz",0);

        txtlenQuiz.setText(Integer.toString(lenQuiz));
        btnstartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });
    }
    private void startQuiz(){
        Intent intent = new Intent(QuizStartActivity.this, QuizActivity.class);
        intent.putExtra("idQuiz",idQuiz);
        startActivity(intent);
    }
    private void insertQuestion(){

        Question question1 = new Question(
                "What is the capital of France?", "MCQ", "Paris", "1",
                "London", "Berlin", "Madrid", "Paris", null);

        Question question2 = new Question(
                "Which planet is known as the Red Planet?", "MCQ", "Mars", "1",
                "Earth", "Mars", "Jupiter", "Saturn", null);

        Question question3 = new Question(
                "Who wrote 'To Kill a Mockingbird'?", "MCQ", "Harper Lee", "1",
                "Mark Twain", "Harper Lee", "J.K. Rowling", "Ernest Hemingway", null);

        Question question4 = new Question(
                "What is the smallest prime number?", "MCQ", "2", "1",
                "1", "2", "3", "5", null);

        Question question5 = new Question(
                "What is the highest prime number?", "MCQ", "5", "1",
                "1", "2", "3", "5", null);

        Question question6 = new Question(
                "How are you tody?", "MCQ", "I'm fine", "1",
                "I'm fine", "So so", "Not bad", "Not yet", null);

        dbHelper.insertDataQuestions(question1);
        dbHelper.insertDataQuestions(question2);
        dbHelper.insertDataQuestions(question3);
        dbHelper.insertDataQuestions(question4);
        dbHelper.insertDataQuestions(question5);
        dbHelper.insertDataQuestions(question6);
    }
    private void insertListenTest(){
        Question question1 = new Question(
                "What country is the champion of euro 2016?", "listen", "Portugal", "2",
                "Portugal", "France", "Germany", "Au", "raw/test_1.m4a");

        dbHelper.insertDataQuestions(question1);
    }
}