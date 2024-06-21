package com.englishtlu.english_learning.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.englishtlu.english_learning.R;
import com.hbb20.CountryCodePicker;


public class LoginWithPhoneNumberActivity extends AppCompatActivity {


   AppCompatButton sendOTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_phone_number);

        countryCodePicker = findViewById(R.id.login_countrycode);
        phoneInput = findViewById(R.id.edtPhoneNumber);
        sendOTP = findViewById(R.id.btnOtp);

        countryCodePicker.registerCarrierNumberEditText(phoneInput);
        sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!countryCodePicker.isValidFullNumber()){
                    phoneInput.setError("Invalid Phone Number");
                    return;
                }
                Intent intent = new Intent(LoginWithPhoneNumberActivity.this, LoginOTPActivity.class);
                intent.putExtra("phone",countryCodePicker.getFullNumberWithPlus());
                startActivity(intent);
            }
        });

    }


}