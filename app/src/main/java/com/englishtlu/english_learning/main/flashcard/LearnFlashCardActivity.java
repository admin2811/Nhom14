package com.englishtlu.english_learning.main.flashcard;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.englishtlu.english_learning.R;

public class LearnFlashCardActivity extends AppCompatActivity {
    private CardView card_front, card_back;
    private boolean isFront;
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

        RelativeLayout card_main = findViewById(R.id.card_main);
        card_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flip_card_anim();
            }
        });
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
}