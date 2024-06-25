package com.englishtlu.english_learning.main.quizz;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.quizz.adapter.CheckAdapter;
import com.englishtlu.english_learning.main.quizz.model.CheckModel;
import com.englishtlu.english_learning.main.quizz.model.DisplayedQuestionModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CheckActivity extends AppCompatActivity {
    private List<CheckModel> questions;
    private HashMap<Integer, String> selectedAnswers;
    ImageView ivBack;
    List<DisplayedQuestionModel> displayedQuestions = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        questions = (List<CheckModel>) getIntent().getSerializableExtra("questions");
        selectedAnswers = (HashMap<Integer, String>) getIntent().getSerializableExtra("selectedAnswers");
        ivBack = findViewById(R.id.ivBack);
        for (CheckModel checkModel : questions) {
            String questionText = "Question: " + checkModel.getQuestion();
            String correctAnswer = "Correct Answer: " + checkModel.getCorrectAnswer();
            String selectedAnswer = "Selected Answer: " + selectedAnswers.get(checkModel.getId());

            DisplayedQuestionModel displayedQuestion = new DisplayedQuestionModel(questionText, correctAnswer, selectedAnswer);
            displayedQuestions.add(displayedQuestion);
        }
        RecyclerView recyclerView = findViewById(R.id.rvResult);
        CheckAdapter adapter = new CheckAdapter(displayedQuestions);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}