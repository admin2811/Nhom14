package com.englishtlu.english_learning.main.flashcard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.MainActivity;

public class FlashCardActivity extends AppCompatActivity {
    FrameLayout addCradFrame, learnFrame;
    ImageView btnBack;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_flash_card);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addCradFrame = findViewById(R.id.cardManagement);
        learnFrame = findViewById(R.id.learnCard);
        btnBack = findViewById(R.id.btnRecall);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashCardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        addCradFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashCardActivity.this, CardManageActivity.class);
                startActivity(intent);
            }
        });
        learnFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FlashCardActivity.this, StartLearnFlashCardActivity.class);
                startActivity(intent);
            }
        });

    }
}