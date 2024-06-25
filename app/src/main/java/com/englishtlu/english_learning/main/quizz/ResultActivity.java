package com.englishtlu.english_learning.main.quizz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.englishtlu.english_learning.main.quizz.model.QuestionModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.englishtlu.english_learning.databinding.ActivityResultBinding;

import com.englishtlu.english_learning.R;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    TextView txtResult,txtTotal;
    AppCompatButton btnQuit,btnSeeReults;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        txtResult = findViewById(R.id.tvScore);
        txtTotal = findViewById(R.id.tvTotal);
        btnQuit = findViewById(R.id.btnOut);
        btnSeeReults = findViewById(R.id.btnSeeResult);
        //Lay các giá trị của điểm và tổng số câu hỏi từ intent
        int score = getIntent().getIntExtra("score",0);
        int total = getIntent().getIntExtra("totalQues",0);
        txtResult.setText(String.valueOf(score));
        txtTotal.setText("/"+total);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, PractieActivity.class);
                startActivity(intent);
            }
        });

        btnSeeReults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}