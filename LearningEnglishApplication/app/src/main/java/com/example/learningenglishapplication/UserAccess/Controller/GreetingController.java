package com.example.learningenglishapplication.UserAccess.Controller;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.learningenglishapplication.R;

public class GreetingController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_greeting_controller);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.greeting), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        switchLogin();
    }

    protected void switchLogin(){
        AppCompatButton login_page_btn = findViewById(R.id.SwitchloginButton);
        login_page_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GreetingController.this, LoginController.class);
                startActivity(intent);
                finish();
            }
        });
    }
}