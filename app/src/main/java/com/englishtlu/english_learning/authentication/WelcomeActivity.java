package com.englishtlu.english_learning.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.report.batterylow.BatteryReceiver;
import com.englishtlu.english_learning.report.networkchange.Networkchange;

public class WelcomeActivity extends AppCompatActivity {

    private BatteryReceiver batteryReceiver = new BatteryReceiver();

    private Networkchange networkChangeReceiver = new Networkchange();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        AppCompatButton btnLogin = (AppCompatButton) findViewById(R.id.login_button);
        AppCompatButton btnRegister = (AppCompatButton) findViewById(R.id.register_button);

        IntentFilter filternetwork = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, filternetwork);

        IntentFilter filterbatery = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(batteryReceiver, filterbatery);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}