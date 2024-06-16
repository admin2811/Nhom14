package com.englishtlu.english_learning.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import  static com.englishtlu.english_learning.R.drawable;
import  static com.englishtlu.english_learning.R.id;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.authentication.model.User;
import com.englishtlu.english_learning.authentication.utility.PreferenceManager;
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
    private EditText edtConPassword,edtPasswordRegister, editTextRegisterFirstName, editTextRegisterLastName, editTextRegisterEmail;
    private ImageView passwordIcon , passwordConIcon;

    private boolean passwordShowing = false;

    private ProgressBar progressBar;
    private static  final String TAG = "RegisterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextRegisterFirstName = findViewById(R.id.edtFirstName);
        editTextRegisterLastName = findViewById(R.id.edtLastName);
        editTextRegisterEmail = findViewById(id.edtEmailRegister);
        passwordIcon = findViewById(R.id.passwordShowIcon);
        edtPasswordRegister = findViewById(R.id.edtPasswordRegister);
        passwordConIcon = findViewById(R.id.passwordConIcon);
        edtConPassword = findViewById(R.id.edtConfirmPassword);
        Button login = findViewById(R.id.btnLogin_1);
        progressBar = findViewById(id.progressBar);

        Button buttonRegister = findViewById(id.btnRegister_1);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textFirstName = editTextRegisterFirstName.getText().toString().trim();
                String textLastName = editTextRegisterLastName.getText().toString().trim();
                String textEmail = editTextRegisterEmail.getText().toString().trim();
                String textPassword = edtPasswordRegister.getText().toString().trim();
                String textConfirmPassword = edtConPassword.getText().toString().trim();

                if(textFirstName.isEmpty()){
                    editTextRegisterFirstName.setError("First Name is required");
                }else if(textLastName.isEmpty()) {
                    editTextRegisterLastName.setError("Last Name is required");
                } else if (textEmail.isEmpty()) {
                    editTextRegisterEmail.setError("Email is required");
                } else if (textPassword.isEmpty()) {
                    edtPasswordRegister.setError("Password is required");
                } else if (textConfirmPassword.isEmpty()) {
                    edtPasswordRegister.setError("Confirm Password is required");
                } else if (!textPassword.equals(textConfirmPassword)) {
                    edtPasswordRegister.setError("Password do not match");
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(textFirstName, textLastName, textEmail, textPassword);

                }
            }
        });
        //Password show
        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(passwordShowing){
                passwordShowing = false;

                edtPasswordRegister.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordIcon.setImageResource(drawable.baseline_visibility_24);
            }else{
                passwordShowing = true;
                edtPasswordRegister.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                passwordIcon.setImageResource(drawable.baseline_visibility_24);

            }
                edtPasswordRegister.setSelection(edtPasswordRegister.length());
            }
        });

        passwordConIcon.setOnClickListener(v -> {
            if(passwordShowing){
                passwordShowing = false;
                edtConPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordConIcon.setImageResource(drawable.baseline_visibility_off_24);
            }else{
                passwordShowing = true;
                edtConPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                passwordIcon.setImageResource(drawable.baseline_visibility_24);
            }
            edtConPassword.setSelection(edtConPassword.length());
        });
        login.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void registerUser(String textFirstName, String textLastName, String textEmail, String textPassword) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail, textPassword).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(RegisterActivity.this, "Register successfully", Toast.LENGTH_SHORT).show();
                FirebaseUser firebaseUser = auth.getCurrentUser();
                assert firebaseUser != null;
                PreferenceManager preferenceManager = new PreferenceManager(RegisterActivity.this);
                preferenceManager.savePassword(textPassword);
                //Send Verification Email
                firebaseUser.sendEmailVerification();
                User user = new User(textFirstName, textLastName, textEmail, textPassword);
                DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");
                referenceProfile.child(firebaseUser.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful()){
                        progressBar.setVisibility(View.GONE);

                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        /*//Open User Profile after successfully  registration
                            Intent intent = new Intent(RegisterActivity.this, UserProfileActivity.class);
                            //To Prevent User from returning back to Register Activity on pressing back button after
                                   registration
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK*/

                           Log.d(TAG, "User details saved");
                       }else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this, "Failed to register", Toast.LENGTH_SHORT).show();
                       }
                   }
               });
            }else{
                progressBar.setVisibility(View.GONE);
                try{
                    throw Objects.requireNonNull(task.getException());
                }catch (FirebaseAuthWeakPasswordException e){
                    edtPasswordRegister.setError("Weak Password: Password must be at least 6 characters");
                    edtPasswordRegister.requestFocus();
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