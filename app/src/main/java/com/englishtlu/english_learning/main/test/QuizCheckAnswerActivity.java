package com.englishtlu.english_learning.main.test;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.test.adapter.AnswerAdapter;
import com.englishtlu.english_learning.main.test.repository.QuizRepository;

public class QuizCheckAnswerActivity extends AppCompatActivity {
    private QuizRepository quizRepository;
    private RecyclerView answerView;
    AnswerAdapter answerAdapter;
    AppCompatButton nextBtn, prevBtn, endBtn;
    private int answerID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz_check_answer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        answerView = findViewById(R.id.answerRC);
        nextBtn = findViewById(R.id.btnGo);
        prevBtn = findViewById(R.id.btnBack);
        endBtn = findViewById(R.id.btnEnd);

        answerID = 0;

        answerAdapter = new AnswerAdapter(QuizCheckAnswerActivity.this, QuizRepository.answersData,new AnswerAdapter.OnQuestionAnsweredListener(){
            @Override
            public void onQuestionAnswered(int position){

            }
        });

        answerView.setAdapter(answerAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        answerView.setLayoutManager(layoutManager);


        setclicklistener();

        endBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void setSnapHelper(){
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(answerView);

        answerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                View view = snapHelper.findSnapView(recyclerView.getLayoutManager());
                answerID = recyclerView.getLayoutManager().getPosition(view);

            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        answerView.setOnTouchListener((v, event) -> true);
    }
    private void setclicklistener(){
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answerID > 0){
                    answerView.smoothScrollToPosition(answerID - 1);
                }
            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answerID < QuizRepository.answersData.size() - 1){
                    answerView.smoothScrollToPosition(answerID + 1);
                }
            }
        });
    }
}