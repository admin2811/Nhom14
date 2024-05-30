package com.englishtlu.english_learning.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.authentication.utility.PreferenceManager;
import com.englishtlu.english_learning.main.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private EditText edtPassword,edtEmail;
    private ImageView passwordIcon;
    private boolean passwordShowing = false;
    Button register,login;
    TextView forgotPassword;
    ProgressBar progressBarLogin;
    private FirebaseAuth authProfile;
    private  static  final String TAG = "LoginActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        passwordIcon = findViewById(R.id.passwordIcon);
        register = findViewById(R.id.btnRegister);
        forgotPassword = findViewById(R.id.forgot_password);
        progressBarLogin = findViewById(R.id.progressBar_login);
        login = findViewById(R.id.btnLogin);
        authProfile =FirebaseAuth.getInstance();
        //Login User
         login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
               String email = edtEmail.getText().toString().trim();
               String password = edtPassword.getText().toString().trim();
                 if (email.isEmpty()) {
                     edtEmail.setError("Email is required");
                     edtEmail.requestFocus();

                 }else if (password.isEmpty()) {
                     edtPassword.setError("Password is required");
                     edtPassword.requestFocus();

                 } else if (password.length() < 6) {
                     edtPassword.setError("Password should be at least 6 characters");
                     edtPassword.requestFocus();

                 }else {
                     progressBarLogin.setVisibility(View.VISIBLE);
                     loginUser(email, password);
                 }
             }
         });

        //Forgot Password
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                @SuppressLint({"InflaterPreface", "Inflate  Params"}) View dialogView = getLayoutInflater().inflate(R.layout.dialog_forgot,null);
                EditText emailBox = dialogView.findViewById(R.id.emailBox);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                dialogView.findViewById(R.id.btnReset).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userEmail = emailBox.getText().toString();

                        if(TextUtils.isEmpty(userEmail)){
                            emailBox.setError("Email is Required");
                            emailBox.requestFocus();
                        }
                        authProfile.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, "Reset Link Sent to your Email", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }else{
                                    Toast.makeText(LoginActivity.this, "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                dialogView.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });
                if(dialog.getWindow() != null){
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
            }
        });
        passwordIcon.setOnClickListener(v -> {
            //Checking if password showing or not
            if(passwordShowing){
                passwordShowing = false;
                edtPassword.setInputType(129);
                passwordIcon.setImageResource(R.drawable.baseline_visibility_off_24);
            }else{
                passwordShowing = true;
                edtPassword.setInputType(128);
                passwordIcon.setImageResource(R.drawable.baseline_visibility_24);
            }
            //move the cursor at last of the text
            edtPassword.setSelection(edtPassword.length());

        });


        register.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser(String email, String password) {
        authProfile.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    //Get instance of the current user
                    FirebaseUser firebaseUser = authProfile.getCurrentUser();
                    //Check if email is verified before user can access their profile
                    assert firebaseUser != null;
                    //Lưu vào shareprefence
                    PreferenceManager preferenceManager = new PreferenceManager(LoginActivity.this);
                    preferenceManager.savePassword(password);
                    if(firebaseUser.isEmailVerified()){

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                        Toast.makeText(LoginActivity.this, "You are logged in now",Toast.LENGTH_SHORT).show();
                    }else{
                        firebaseUser.sendEmailVerification();
                        authProfile.signOut();
                        showAlertDialog();
                        Toast.makeText(LoginActivity.this, "Check your email to verify your account", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    try {
                        throw Objects.requireNonNull(task.getException());
                    }catch (FirebaseAuthInvalidUserException e){
                        edtEmail.setError("User does not exists or is no longer valid. Please register again");
                        edtEmail.requestFocus();
                        progressBarLogin.setVisibility(View.GONE);
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        edtEmail.setError("Invalid credentials. Please check your email and password");
                        edtEmail.requestFocus();
                        progressBarLogin.setVisibility(View.GONE);
                    }catch (Exception e) {
                        Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        progressBarLogin.setVisibility(View.GONE);
                    }
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Email Verification");
        builder.setMessage("Please verify your email before login");
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        AlertDialog alertDialog =builder.create();

        alertDialog.show();
    }

}