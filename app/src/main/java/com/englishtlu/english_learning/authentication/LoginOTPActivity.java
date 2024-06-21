package com.englishtlu.english_learning.authentication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.englishtlu.english_learning.databinding.ActivityLoginOtpactivityBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneAuthOptions;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class LoginOTPActivity extends AppCompatActivity {
    String phoneNumber, verificationCode;
    EditText otpInput;
    AppCompatButton verifyOTP;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    PhoneAuthProvider.ForceResendingToken resendingToken;
    Long timeoutSeconds = 60L;
    TextView resendOTP;

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otpactivity);
        otpInput = findViewById(R.id.edtOtp);
        verifyOTP = findViewById(R.id.btnVeritfy);
        progressBar = findViewById(R.id.progressBar6);
        resendOTP = findViewById(R.id.resedn_otp_textView);


        phoneNumber = getIntent().getExtras().getString("phone");
        Toast.makeText(this, phoneNumber, Toast.LENGTH_SHORT).show();
        sendOTP(phoneNumber, false);
        verifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enterOtp = otpInput.getText().toString();
                if (enterOtp.isEmpty()) {
                    Toast.makeText(LoginOTPActivity.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                    return;
                }
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, enterOtp);
                signIn(credential);
            }
        });
        resendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOTP(phoneNumber, true);
            }
        });
    }

    private void signIn(PhoneAuthCredential credential) {
        setInProgress(true);
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                setInProgress(true);
                if (task.isSuccessful()) {
                    Intent intent = new Intent(LoginOTPActivity.this, MainActivity.class);
                    intent.putExtra("phone", phoneNumber);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "OTP verification failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setInProgress(boolean inProgress) {
        new Handler(Looper.getMainLooper()).post(() -> {
            if (inProgress) {
                progressBar.setVisibility(View.VISIBLE);
                verifyOTP.setEnabled(false);
            } else {
                progressBar.setVisibility(View.GONE);
                verifyOTP.setEnabled(true);
            }
        });
    }

    private void sendOTP(String phoneNumber, boolean isResend) {
        startResendTimer();
        setInProgress(true);
        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(timeoutSeconds, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signIn(phoneAuthCredential);
                                setInProgress(false);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(getApplicationContext(), "OTP verification failed", Toast.LENGTH_SHORT).show();
                                setInProgress(false);
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationCode = s;
                                resendingToken = forceResendingToken;
                                Toast.makeText(getApplicationContext(), "OTP send success", Toast.LENGTH_SHORT).show();
                                setInProgress(false);
                            }
                        });
        if (isResend) {
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        } else {
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }


    }


    private void startResendTimer() {
        resendOTP.setEnabled(false);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        timeoutSeconds--;
                        resendOTP.setText("Resend OTP in " + timeoutSeconds + " seconds");
                        if (timeoutSeconds <= 0) {
                            timeoutSeconds = 60L;
                            timer.cancel();
                            resendOTP.setEnabled(true);
                        }
                    }
                });
            }
        }, 0, 1000);
    }
}
