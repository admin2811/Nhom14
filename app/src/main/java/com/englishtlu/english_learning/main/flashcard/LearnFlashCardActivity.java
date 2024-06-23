package com.englishtlu.english_learning.main.flashcard;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.flashcard.adapter.ListCardAdapter;
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
    private AppCompatButton nextCardbtn;
    private int currentCardIndex = 0;
    private boolean isFront;
    String nameDesk;
    FirebaseAuth auth;
    String userId;
    List<String> cards;
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
            }
        });
        card_main.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                nextCard();
                return true;
            }
        });

        nextCardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextCard();
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
                        tv_question.setText(question);
                        tv_answer.setText(answer);
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

        setout.setTarget(isFront ? card_front: card_back);
        setin.setTarget(isFront ? card_back: card_front);

        setout.start();
        setin.start();

        isFront = !isFront;
    }
    private void nextCard() {
        if (currentCardIndex < cards.size() - 1) {
            currentCardIndex++;
        } else {
            currentCardIndex = 0; // Loop back to the first card if at the end
        }
        displayCurrentCard();
        if (!isFront) {
            flip_card_anim();
        }
    }

}