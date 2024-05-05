package com.example.learningenglishapplication.UserAccess.Controller;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.learningenglishapplication.Home.Controller.HomeController;
import com.example.learningenglishapplication.Translate.Controller.TranslateController;
import com.example.learningenglishapplication.R;


public class MainPageController extends AppCompatActivity {
    private LinearLayout btnNavigate;
    private FrameLayout frameLayout;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Fragment currentFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_page_controller);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        navigateHome();
        navigateTranslate();
    }

    @Override
    protected void onResume(){
        super.onResume();

        btnNavigate = findViewById(R.id.homeBtn);
        btnNavigate.performClick();
    }

    protected void navigateHome(){
        btnNavigate = findViewById(R.id.homeBtn);
        btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getSupportFragmentManager();
                currentFragment = fragmentManager.findFragmentById(R.id.framelayout);
                if (currentFragment == null || !(currentFragment instanceof HomeController)) {
                    fragmentTransaction = fragmentManager.beginTransaction();
                    if (currentFragment != null) {
                        fragmentTransaction.hide(currentFragment);
                    }
                    fragmentTransaction.addToBackStack(null);
                    currentFragment = new HomeController();
                    fragmentTransaction.add(R.id.framelayout, currentFragment);
                    fragmentTransaction.commit();
                }
            }
        });
    }

    protected  void navigateTranslate(){
        btnNavigate = findViewById(R.id.tranlateBtn);
        btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getSupportFragmentManager();
                currentFragment = fragmentManager.findFragmentById(R.id.framelayout);
                if(currentFragment == null || !(currentFragment instanceof TranslateController)){
                    fragmentTransaction = fragmentManager.beginTransaction();
                    if (currentFragment != null) {
                        fragmentTransaction.hide(currentFragment);
                    }
                    fragmentTransaction.addToBackStack(null);
                    currentFragment = new TranslateController();
                    fragmentTransaction.add(R.id.framelayout, currentFragment);
                    fragmentTransaction.commit();
                }
            }
        });
    }
}