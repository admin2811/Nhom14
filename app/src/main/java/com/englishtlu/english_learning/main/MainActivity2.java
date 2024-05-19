package com.englishtlu.english_learning.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.authentication.model.User;
import com.englishtlu.english_learning.main.chatbot.ChatBotFragment;
import com.englishtlu.english_learning.main.home.view.HomeFragment;
import com.englishtlu.english_learning.main.profile.ProfileFragment;
import com.englishtlu.english_learning.main.translate.TranslateFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity2 extends AppCompatActivity {

    private  String lastName,getLastName;
    private FirebaseAuth auth;
    private int selectedTab = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final LinearLayout homeLayout = findViewById(R.id.homeLayout);
        final LinearLayout translateLayout = findViewById(R.id.translateLayout);
        final LinearLayout chatbotLayout = findViewById(R.id.chatbotLayout);
        final LinearLayout profileLayout = findViewById(R.id.profileLayout);

        final ImageView homeImage = findViewById(R.id.homeImage);
        final ImageView translateImage = findViewById(R.id.translateImage);
        final ImageView chatbotImage = findViewById(R.id.chatbotImage);
        final ImageView profileImage = findViewById(R.id.profileImage);

        final TextView homeText = findViewById(R.id.homeTxt);
        final TextView translateText = findViewById(R.id.translateTxt);
        final TextView chatbotText = findViewById(R.id.chatbotTxt);
        final TextView profileText = findViewById(R.id.profileTxt);
        //in ra userId để kiểm tra

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if(user == null){
            Toast.makeText(this, "User is null", Toast.LENGTH_SHORT).show();
        }else{
             getLastName(user);
        }
        getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainerView, HomeFragment.class,null).commit();

        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if home is already selected or not.
                if(selectedTab != 1){

                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true).replace(R.id.fragmentContainerView, HomeFragment.class, null).commit();
                    // unselect other tabs execpet home tab
                    translateText.setVisibility(View.GONE);
                    chatbotText.setVisibility(View.GONE);
                    profileText.setVisibility(View.GONE);

                    translateImage.setImageResource(R.drawable.icon_translate);
                    chatbotImage.setImageResource(R.drawable.icon_chatbot);
                    profileImage.setImageResource(R.drawable.icon_profile);

                    translateLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    chatbotLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    // select home tab
                    homeText.setVisibility(View.VISIBLE);
                    homeImage.setImageResource(R.drawable.icon_home_selected);
                    homeLayout.setBackgroundResource(R.drawable.round_back_home_100);

                    //create animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f,1f,1f, Animation.RELATIVE_TO_SELF,0.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    homeLayout.startAnimation(scaleAnimation);

                    //set 1st tab as selected
                    selectedTab = 1;


                }
            }
        });

        translateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check if translate is already selected or not.
                if(selectedTab != 2){

                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true).replace(R.id.fragmentContainerView, TranslateFragment.class, null).commit();

                    // unselect other tabs execpet translate tab
                    homeText.setVisibility(View.GONE);
                    chatbotText.setVisibility(View.GONE);
                    profileText.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.icon_home);
                    chatbotImage.setImageResource(R.drawable.icon_chatbot);
                    profileImage.setImageResource(R.drawable.icon_profile);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    chatbotLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    // select home tab
                    translateText.setVisibility(View.VISIBLE);
                    translateImage.setImageResource(R.drawable.icon_translate_selected);
                    translateLayout.setBackgroundResource(R.drawable.round_back_home_100);

                    //create animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f,1f,1f, Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    translateLayout.startAnimation(scaleAnimation);

                    //set 1st tab as selected
                    selectedTab = 2;

                }
            }
        });

        chatbotLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTab != 3){
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true).replace(R.id.fragmentContainerView, ChatBotFragment.class, null).commit();
                    // unselect other tabs execpet home tab
                    homeText.setVisibility(View.GONE);
                    translateText.setVisibility(View.GONE);
                    profileText.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.icon_home);
                    translateImage.setImageResource(R.drawable.icon_translate);
                    profileImage.setImageResource(R.drawable.icon_profile);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    translateLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    profileLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    // select home tab
                    chatbotText.setVisibility(View.VISIBLE);
                    chatbotImage.setImageResource(R.drawable.icon_chatbot_selected);
                    chatbotLayout.setBackgroundResource(R.drawable.round_back_home_100);

                    //create animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f,1f,1f, Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    chatbotLayout.startAnimation(scaleAnimation);

                    //set 1st tab as selected
                    selectedTab = 3;

                }
            }
        });
        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTab != 4){

                    getSupportFragmentManager().beginTransaction().setReorderingAllowed(true).replace(R.id.fragmentContainerView, ProfileFragment.class, null).commit();
                    // unselect other tabs execpet home tab
                    homeText.setVisibility(View.GONE);
                    translateText.setVisibility(View.GONE);
                    chatbotText.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.icon_home);
                    translateImage.setImageResource(R.drawable.icon_translate);
                    chatbotImage.setImageResource(R.drawable.icon_chatbot);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    translateLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    chatbotLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    // select home tab
                    profileText.setVisibility(View.VISIBLE);
                    profileImage.setImageResource(R.drawable.icon_profile_selected);
                    profileLayout.setBackgroundResource(R.drawable.round_back_home_100);

                    //create animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f,1f,1f, Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    profileLayout.startAnimation(scaleAnimation);

                    //set 1st tab as selected
                    selectedTab = 4;

                }
            }
        });
    }

    private void getLastName(FirebaseUser user) {
            String userID = user.getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user != null){
                    lastName = user.getTextLastName();
                    updateHomeFragment(lastName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity2.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateHomeFragment(String lastName) {
        Bundle bundle = new Bundle();
        bundle.putString("LAST_NAME", lastName);
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainerView, homeFragment)
                .commit();
    }
}