package com.example.myfirebaseapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextRegisterFirstName, editTextRegisterLastName, editTextRegisterEmail, editTextRegisterPassword, editTextRegisterConfirmPassword;

    private ProgressBar progressBar;
    private static  final String TAG = "RegisterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);

        editTextRegisterFirstName = findViewById(R.id.edtFirstName);
        editTextRegisterLastName = findViewById(R.id.edtLastName);
        editTextRegisterEmail = findViewById(R.id.edtEmailRegister);
        editTextRegisterPassword = findViewById(R.id.edtPasswordRegister);
        editTextRegisterConfirmPassword = findViewById(R.id.edtConfirmPassword);
        progressBar = findViewById(R.id.progressBar);

        Button buttonRegister = findViewById(R.id.btnRegister_1);
        buttonRegister.setOnClickListener(v -> {
            String textFirstName = editTextRegisterFirstName.getText().toString();
            String textLastName = editTextRegisterLastName.getText().toString();
            String textEmail = editTextRegisterEmail.getText().toString();
            String textPassword = editTextRegisterPassword.getText().toString();
            String textConfirmPassword = editTextRegisterConfirmPassword.getText().toString();

            if(textFirstName.isEmpty()){
                editTextRegisterFirstName.setError("First Name is required");
            }else if(textLastName.isEmpty()) {
                editTextRegisterLastName.setError("Last Name is required");
            } else if (textEmail.isEmpty()) {
                editTextRegisterEmail.setError("Email is required");
            } else if (textPassword.isEmpty()) {
                editTextRegisterPassword.setError("Password is required");
            } else if (textConfirmPassword.isEmpty()) {
                editTextRegisterConfirmPassword.setError("Confirm Password is required");
            } else if (!textPassword.equals(textConfirmPassword)) {
                editTextRegisterConfirmPassword.setError("Password do not match");
            } else {
                progressBar.setVisibility(View.VISIBLE);
                registerUser(textFirstName, textLastName, textEmail, textPassword);

            }

        });
    }

    private void registerUser(String textFirstName, String textLastName, String textEmail, String textPassword) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail, textPassword).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(RegisterActivity.this, "Register successfully", Toast.LENGTH_SHORT).show();
                FirebaseUser firebaseUser = auth.getCurrentUser();
                assert firebaseUser != null;
                //Send Verification Email
                firebaseUser.sendEmailVerification();


                //Store User into Store Data in FireBase
                ReadWriteUserDetails readWriteUserDetails = new ReadWriteUserDetails(textFirstName, textLastName);

                //Extracting the user details from the database
                DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");
                referenceProfile.child(firebaseUser.getUid()).setValue(readWriteUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);

                            //Redirect to Login page
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);

                            /*//Open User Profile after successfully  registration
                            Intent intent = new Intent(RegisterActivity.this, UserProfileActivity.class);
                            //To Prevent User from returning back to Register Activity on pressing back button after
                                   registration
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK*/

                            Log.d(TAG, "User details saved");
                        }else{
                            Log.e(TAG, Objects.requireNonNull(Objects.requireNonNull(task.getException()).getMessage()));
                        }
                    }
                });
            }else{
                try{
                    throw Objects.requireNonNull(task.getException());
                }catch (FirebaseAuthWeakPasswordException e){
                    editTextRegisterPassword.setError("Weak Password: Password must be at least 6 characters");
                    editTextRegisterPassword.requestFocus();
                }catch (FirebaseAuthInvalidCredentialsException | FirebaseAuthInvalidUserException e){
                    editTextRegisterEmail.setError("Your Email is invalid or already exist");
                    editTextRegisterEmail.requestFocus();
                }catch (FirebaseAuthUserCollisionException e){
                    editTextRegisterEmail.setError("Email already exist");
                    editTextRegisterEmail.requestFocus();
                } catch (Exception e){
                    Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                    Toast.makeText(RegisterActivity.this, "Error: " + Objects.requireNonNull(task.getException()).  getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}