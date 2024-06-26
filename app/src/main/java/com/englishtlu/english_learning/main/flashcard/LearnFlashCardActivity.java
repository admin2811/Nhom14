package com.englishtlu.english_learning.main.flashcard;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.englishtlu.english_learning.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LearnFlashCardActivity extends AppCompatActivity {
    private CardView card_front, card_back;
    private TextView tv_question, tv_answer;
    private TextView tv_end;
    private ImageView btnBack;
    private AppCompatButton nextCardbtn, btnEndTask;
    private int currentCardIndex = 0;
    private boolean isFront = true;
    String nameDesk;
    FirebaseAuth auth;
    String userId;
    List<String> cards;
    int lenDesks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn_flash_card);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        card_front = findViewById(R.id.card_front);
        card_back = findViewById(R.id.card_back);
        tv_question = findViewById(R.id.tv_question);
        tv_answer = findViewById(R.id.tv_answer);
        nextCardbtn = findViewById(R.id.btnNextCard);
        btnEndTask = findViewById(R.id.EndTask);
        tv_end = findViewById(R.id.TheEnd);

        auth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
        }

        cards = new ArrayList<>();

        nameDesk = getIntent().getStringExtra("nameDesk");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Flashcards").child(userId).child(nameDesk);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    cards.clear();
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        cards.add(childSnapshot.getKey());
                    }
                    lenDesks = cards.size();
                    cards.remove(lenDesks-1);
                    if (!cards.isEmpty()) {
                        displayCurrentCard();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        RelativeLayout card_main = findViewById(R.id.card_main);
        card_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flip_card_anim();
                if (isFront) {
                    card_front.setVisibility(View.VISIBLE);
                    card_back.setVisibility(View.INVISIBLE);
                } else {
                    card_front.setVisibility(View.INVISIBLE);
                    card_back.setVisibility(View.VISIBLE);
                }
            }
        });

        nextCardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentCardIndex == (cards.size() - 1)){
                    tv_end.setVisibility(View.VISIBLE);
                    card_main.setVisibility(View.INVISIBLE);
                    nextCardbtn.setVisibility(View.INVISIBLE);
                } else {
                    nextCard();
                }
            }
        });

        btnEndTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }
    private void displayCurrentCard() {
        if (currentCardIndex < cards.size()) {
            String currentCard = cards.get(currentCardIndex);
            DatabaseReference cardRef = FirebaseDatabase.getInstance().getReference("Flashcards").child(userId).child(nameDesk).child(currentCard);
            cardRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String question = snapshot.child("question").getValue(String.class);
                        String answer = snapshot.child("answer").getValue(String.class);
                        Toast.makeText(LearnFlashCardActivity.this,answer,Toast.LENGTH_LONG).show();
                        tv_question.setText(question);
                        tv_answer.setText(answer);
                        if (isFront) {
                            card_front.setVisibility(View.VISIBLE);
                            card_back.setVisibility(View.INVISIBLE);
                        } else {
                            card_front.setVisibility(View.INVISIBLE);
                            card_back.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle error
                }
            });
        }
    }

    private void flip_card_anim(){
        AnimatorSet setout = (AnimatorSet) AnimatorInflater.loadAnimator(LearnFlashCardActivity.this,R.animator.card_flip_out);
        AnimatorSet setin = (AnimatorSet) AnimatorInflater.loadAnimator(LearnFlashCardActivity.this,R.animator.card_flip_in);

        if (isFront) {
            setout.setTarget(card_front);
            setin.setTarget(card_back);
        } else {
            setout.setTarget(card_back);
            setin.setTarget(card_front);
        }

        setout.start();
        setin.start();

        isFront = !isFront;
    }
    private void nextCard() {
        if (currentCardIndex < cards.size() - 1) {
            currentCardIndex++;
        }
        displayCurrentCard();
        if (!isFront) {
            flip_card_anim();
        }
    }

}