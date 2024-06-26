package com.englishtlu.english_learning.main.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.test.database.QuizDatabase;
import com.englishtlu.english_learning.main.test.model.Question;
import com.englishtlu.english_learning.main.test.repository.QuizRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuizStartActivity extends AppCompatActivity {
    private int lenQuiz;
    private int idQuiz;
    String userId;
    String nameQuiz;
    FirebaseAuth auth;
    QuizRepository quizRepository;
    private TextView txtlenQuiz, highestscore;
    private ImageView btnBack;
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
        auth = FirebaseAuth.getInstance();

        txtlenQuiz = findViewById(R.id.lenQuiz);
        btnstartQuiz = findViewById(R.id.btnstartQuiz);
        highestscore = findViewById(R.id.txtHighetscore);
        btnBack = findViewById(R.id.btnRecall);

        lenQuiz = getIntent().getIntExtra("lenQuiz",0);
        idQuiz = getIntent().getIntExtra("idQuiz",0);
        nameQuiz = getIntent().getStringExtra("nameQuiz");

        txtlenQuiz.setText(Integer.toString(lenQuiz));

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
        }

        quizRepository = new QuizRepository(this);
        quizRepository.nuTrue = 0;
        quizRepository.nuWrong = 0;
        quizRepository.nuNoChoice = 0;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("QuizResult").child(userId).child("quiz" + Integer.toString(idQuiz)).child("result");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String result = snapshot.getValue(String.class);
                    highestscore.setText(result);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnstartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizStartActivity.this, CourseActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void startQuiz(){
        Intent intent = new Intent(QuizStartActivity.this, QuizActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("idQuiz",idQuiz);
        intent.putExtra("nameQuiz",nameQuiz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
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