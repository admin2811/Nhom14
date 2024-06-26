package com.englishtlu.english_learning.main.test;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.test.adapter.QuizListAdapter;
import com.englishtlu.english_learning.main.test.model.Question;
import com.englishtlu.english_learning.main.test.model.Quiz;
import com.englishtlu.english_learning.main.test.repository.QuizRepository;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class CourseActivity extends AppCompatActivity {
    private QuizRepository quizRepository;
    private RecyclerView quizView;
    private QuizListAdapter quizListAdapter;
    private FirebaseAuth auth;
    ArrayList<Question> questionList = new ArrayList<>();
    private static ArrayList<Quiz> quizsData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_course);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        quizView = findViewById(R.id.quizList);
        quizRepository = new QuizRepository(this);
        initData();
        quizsData = quizRepository.readQuizList();
        quizListAdapter = new QuizListAdapter(this, quizsData, new QuizListAdapter.QuizItemClickListener() {
            @Override
            public void onQuizItemClick(ArrayList<Question> questionList, int id, String name) {
                Intent intent = new Intent(CourseActivity.this, QuizStartActivity.class);
                intent.putExtra("lenQuiz",questionList.size());
                intent.putExtra("nameQuiz",name);
                intent.putExtra("idQuiz",id);

                startActivity(intent);
            }
        });

        quizView.setAdapter(quizListAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        quizView.setLayoutManager(layoutManager);
    }
    private void initData(){
        for (int i = 0; i < 5; i++){
            String quizName = "quiz" + (i+1);
            Quiz quiz = new Quiz(0,quizName);
            quizRepository.insertData(quiz);
        }
    }
}