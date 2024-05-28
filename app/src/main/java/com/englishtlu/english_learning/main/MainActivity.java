package com.englishtlu.english_learning.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.englishtlu.english_learning.main.chatbot.ChatBotActivity;
import com.englishtlu.english_learning.main.dictionary.DictionaryFragment;
import com.englishtlu.english_learning.main.home.view.HomeFragment;
import com.englishtlu.english_learning.main.profile.ProfileFragment;
import com.englishtlu.english_learning.main.translate.TranslateFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private  String lastName,getLastName;
    private FirebaseAuth auth;
    private int selectedTab = 1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final LinearLayout homeLayout = findViewById(R.id.homeLayout);
        final LinearLayout translateLayout = findViewById(R.id.translateLayout);
         final LinearLayout dicLayout = findViewById(R.id.dicLayout);
        final LinearLayout profileLayout = findViewById(R.id.profileLayout);

        final ImageView homeImage = findViewById(R.id.homeImage);
        final ImageView translateImage = findViewById(R.id.translateImage);
        final ImageView dicImage = findViewById(R.id.dicImage);
        final ImageView profileImage = findViewById(R.id.profileImage);

        final TextView homeText = findViewById(R.id.homeTxt);
        final TextView translateText = findViewById(R.id.translateTxt);
        final TextView dicText = findViewById(R.id.dicTxt);
        final TextView profileText = findViewById(R.id.profileTxt);
        final FloatingActionButton chatBot = findViewById(R.id.fab_button);
        chatBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChatBotActivity.class);
                startActivity(intent);

            }
        });
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
                    if(user == null){
                        Toast.makeText(MainActivity.this, "User is null", Toast.LENGTH_SHORT).show();
                    }else{
                        getLastName(user);
                    }
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true).replace(R.id.fragmentContainerView, HomeFragment.class, null).commit();
                    // unselect other tabs execpet home tab
                    translateText.setVisibility(View.GONE);
                    dicText.setVisibility(View.GONE);
                    profileText.setVisibility(View.GONE);

                    translateImage.setImageResource(R.drawable.icon_translate);
                    dicImage.setImageResource(R.drawable.dictionary);
                    profileImage.setImageResource(R.drawable.icon_profile);

                    translateLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    dicLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
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
                    dicText.setVisibility(View.GONE);
                    profileText.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.icon_home);
                    dicImage.setImageResource(R.drawable.dictionary);
                    profileImage.setImageResource(R.drawable.icon_profile);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    dicLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
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

        dicLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedTab != 3){
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true).replace(R.id.fragmentContainerView, DictionaryFragment.class, null).commit();
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
                    dicText.setVisibility(View.VISIBLE);
                    dicImage.setImageResource(R.drawable.dictionary_selected);
                    dicLayout.setBackgroundResource(R.drawable.round_back_home_100);

                    //create animation
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f,1f,1f, Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                   dicLayout.startAnimation(scaleAnimation);

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
                    dicText.setVisibility(View.GONE);

                    homeImage.setImageResource(R.drawable.icon_home);
                    translateImage.setImageResource(R.drawable.icon_translate);
                    dicImage.setImageResource(R.drawable.dictionary);

                    homeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    translateLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    dicLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

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
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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