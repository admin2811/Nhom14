package com.example.myfirebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextLoginEmail, editTextLoginPassword;
    private ProgressBar progressBarLogin;
    private FirebaseAuth authProfile;
    TextView forgotPassword;
    //Show Hide Password using Eye Icon


    private  static  final String TAG = "LoginActivity";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Objects.requireNonNull(getActionBar()).setTitle("Login");
        editTextLoginEmail = findViewById(R.id.editText_login_email);
        editTextLoginPassword = findViewById(R.id.editText_login_password);
        progressBarLogin = findViewById(R.id.progressBar_login);
        authProfile = FirebaseAuth.getInstance();
        ImageView imageViewShowHidePassword = findViewById(R.id.imageView_show_hide_pwd);
        imageViewShowHidePassword.setImageResource(R.drawable.ic_hide_pwd);
        forgotPassword = findViewById(R.id.forgot_password);
        imageViewShowHidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextLoginPassword.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    //If password is visible then hide it
                    editTextLoginPassword.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
                    //Change Icon
                    imageViewShowHidePassword.setImageResource(R.drawable.ic_hide_pwd);
                }else{
                    editTextLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowHidePassword.setImageResource(R.drawable.ic_show_pwd);

                }
            }
        });
        //Login User
        Button buttonLogin = findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextLoginEmail.getText().toString().trim();
                String password = editTextLoginPassword.getText().toString().trim();
                if (email.isEmpty()) {
                    editTextLoginEmail.setError("Email is required");
                    editTextLoginEmail.requestFocus();
                    return;
                }else if (password.isEmpty()) {
                    editTextLoginPassword.setError("Password is required");
                    editTextLoginPassword.requestFocus();
                    return;
                } else if (password.length() < 6) {
                    editTextLoginPassword.setError("Password should be at least 6 characters");
                    editTextLoginPassword.requestFocus();
                    return;
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
                @SuppressLint("InflateParams") View dialogView = getLayoutInflater().inflate(R.layout.dialog_forgot, null);
                EditText emailBox = dialogView.findViewById(R.id.emailBox);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                dialogView.findViewById(R.id.btnReset).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userEmail = emailBox.getText().toString();

                        if(TextUtils.isEmpty(userEmail) && !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
                            emailBox.setError("Enter a valid email address");
                            emailBox.requestFocus();
                        }
                        authProfile.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, "Password reset link sent to your email address",Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }else{
                                    Toast.makeText(LoginActivity.this, "Failed to send reset email",Toast.LENGTH_SHORT).show();
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
    }

    private void loginUser(String email, String password) {
        authProfile.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    //Get instance of the current user
                    FirebaseUser firebaseUser = authProfile.getCurrentUser();

                    //Check if email is verified before user can access their profile
                    assert firebaseUser != null;
                    if(firebaseUser.isEmailVerified()){
                        Toast.makeText(LoginActivity.this, "You are logged in now",Toast.LENGTH_SHORT).show();

                        //Open the profile activity
                       /* startActivity(new Intent(LoginActivity.this, UserProfileActivity.class));
                        finish();*/
                    }else{
                       /* firebaseUser.sendEmailVerification();
                        authProfile.signOut();
                        showAlterDialog(); */
                    }
                }else{
                    try {
                        throw Objects.requireNonNull(task.getException());
                    }catch (FirebaseAuthInvalidUserException e){
                        editTextLoginEmail.setError("User does not exists or is no longer valid. Please register again");
                        editTextLoginEmail.requestFocus();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        editTextLoginEmail.setError("Invalid credentials. Please check your email and password");
                        editTextLoginEmail.requestFocus();
                    }catch (Exception e) {
                        Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
                progressBarLogin.setVisibility(View.GONE);
            }
        });
    }

    private void showAlterDialog() {
        //Setup the Alter Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Email Verification");
        builder.setMessage("Please verify your email before you can login");
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_APP_EMAIL);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                authProfile.signOut();
            }
        });
        //Create the AlertDialog
        AlertDialog alertDialog = builder.create();

        //Sgiw the AlterDialog
        alertDialog.show();
    }
    //Check if user is already logged in. In such case, straight away open the profile activity
@Override
    protected void onStart(){
        super.onStart();
        if(authProfile.getCurrentUser() != null){
            Toast.makeText(LoginActivity.this, "You are already logged in", Toast.LENGTH_SHORT).show();

            //Start the UserProfileActivity
           /* startActivity(new Intent(LoginActivity.this, UserProfileActivity.class));
            finish();*/
        }else{
            Toast.makeText(LoginActivity.this, "Please login", Toast.LENGTH_SHORT).show();
        }
}

}