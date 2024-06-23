package com.englishtlu.english_learning.main.quizz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.englishtlu.english_learning.R;

public class StartActivity extends AppCompatActivity {
    
    AppCompatButton btnStartPractice,btnBackToHome;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        int category_id = getIntent().getIntExtra("CATEGORY_ID", -1);
        if(category_id == -1){
            // Handle the case where CATEGORY_ID is not passed
            Toast.makeText(this, "Invalid category ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        btnBackToHome = findViewById(R.id.btnBack);
        btnBackToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnStartPractice = findViewById(R.id.btnStart);
        btnStartPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, QuesstionActivity.class);
                intent.putExtra("CATEGORY_ID", category_id);
                startActivity(intent);
            }
        });

    }

}