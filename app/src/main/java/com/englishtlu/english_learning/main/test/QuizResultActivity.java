package com.englishtlu.english_learning.main.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.test.repository.QuizRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class QuizResultActivity extends AppCompatActivity {
    private TextView wrongAnswer, trueAnswer, ngAnswer, Earnscore;
    private int quizID;
    QuizRepository quizRepository;
    AppCompatButton btnEnd, btnCheck;
    FirebaseAuth auth;
    String userId;
    String nameQuiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        auth = FirebaseAuth.getInstance();
        trueAnswer = findViewById(R.id.noTrue);
        wrongAnswer = findViewById(R.id.noFalse);
        ngAnswer = findViewById(R.id.noNG);
        btnEnd = findViewById(R.id.btnEndquiz);
        Earnscore = findViewById(R.id.txtEarnScore);
        btnCheck = findViewById(R.id.btnCheck);


        quizRepository = new QuizRepository(this);

        trueAnswer.setText(Integer.toString(quizRepository.nuTrue));
        wrongAnswer.setText(Integer.toString(quizRepository.nuWrong));
        ngAnswer.setText(Integer.toString(quizRepository.nuNoChoice));

        quizID = getIntent().getIntExtra("idQuiz",0);
        nameQuiz = getIntent().getStringExtra("nameQuiz");


        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
        }

        Earnscore.setText("You've scored " + quizRepository.nuTrue + " points");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("QuizResult").child(userId).child(Integer.toString(quizID));
        /*Map<String, Object> values = new HashMap<>();
        values.put("result",Integer.toString(quizRepository.nuTrue));
        values.put("namequiz",nameQuiz);
        databaseReference.updateChildren(values)
                .addOnCompleteListener(aVoid -> {

                })
                .addOnFailureListener(e -> {

                });*/

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // If it exists, compare the current result with the new result
                    Integer currentResult = Integer.parseInt(snapshot.child("result").getValue(String.class));
                    int newResult = quizRepository.nuTrue;
                    if (currentResult == null || currentResult < newResult) {
                        // Update only if current result is null or less than new result
                        Map<String, Object> values = new HashMap<>();
                        values.put("result", Integer.toString(quizRepository.nuTrue));
                        values.put("namequiz", nameQuiz);
                        databaseReference.updateChildren(values)
                                .addOnCompleteListener(task -> {
                                    // Handle success
                                })
                                .addOnFailureListener(e -> {
                                    // Handle failure
                                });
                    }
                } else {
                    // If it doesn't exist, create a new entry
                    Map<String, Object> values = new HashMap<>();
                    values.put("result", Integer.toString(quizRepository.nuTrue));
                    values.put("namequiz", nameQuiz);
                    databaseReference.updateChildren(values)
                            .addOnCompleteListener(task -> {
                                // Handle success
                            })
                            .addOnFailureListener(e -> {
                                // Handle failure
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });


        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizResultActivity.this,QuizCheckAnswerActivity.class);
                startActivity(intent);
            }
        });
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizResultActivity.this,CourseActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}