package com.englishtlu.english_learning.main.profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.appcompat.widget.AppCompatButton;

import com.englishtlu.english_learning.R;

public class RateUsDialog extends Dialog {
    private float userRate=0;
    public RateUsDialog(@NonNull Context context){
        super(context);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_us_dialog_layout);
        final AppCompatButton rateNowBtn = findViewById(R.id.rateNowBtn);
        final AppCompatButton laterBtn = findViewById(R.id.laterBtn);
        final RatingBar ratingBar = findViewById(R.id.ratingBar);
        final ImageView ratingImage = findViewById(R.id.ratingImage);

        rateNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hiển thị thông báo cảm ơn
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Thank you");
                builder.setMessage("Thank you for your feedback");
                builder.setPositiveButton("OK",null);
                builder.show();
                dismiss();
            }
        });
        laterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if(rating <= 1 ){
                    ratingImage.setImageResource(R.drawable.one_star);
                } else if (rating <= 2) {
                    ratingImage.setImageResource(R.drawable.two_star);
                } else if (rating <= 3) {
                    ratingImage.setImageResource(R.drawable.three_star);
                } else if (rating <= 4) {
                    ratingImage.setImageResource(R.drawable.four_star);
                } else if (rating <= 5) {
                    ratingImage.setImageResource(R.drawable.five_star);

                }
                animateRatingImage(ratingImage);

                userRate= rating;
            }
        });
    }

    private void animateRatingImage(ImageView ratingImage){
        // Animation code here

    }

}
