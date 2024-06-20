package com.englishtlu.english_learning.main.test;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.test.repository.QuizRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QuizResultActivity extends AppCompatActivity {
    private TextView wrongAnswer, trueAnswer, ngAnswer;
    private int quizID;
    QuizRepository quizRepository;
    FirebaseAuth auth;
    String userId;
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

        quizRepository = new QuizRepository(this);

        trueAnswer.setText(Integer.toString(quizRepository.nuTrue));
        wrongAnswer.setText(Integer.toString(quizRepository.nuWrong));
        ngAnswer.setText(Integer.toString(quizRepository.nuNoChoice));

        quizID = getIntent().getIntExtra("idQuiz",0);

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("QuizResult").child(userId).child("quiz" + Integer.toString(quizID));
        databaseReference.child("result").setValue(Integer.toString(quizRepository.nuTrue))
                .addOnCompleteListener(aVoid -> {

                })
                .addOnFailureListener(e -> {

                });
    }
}