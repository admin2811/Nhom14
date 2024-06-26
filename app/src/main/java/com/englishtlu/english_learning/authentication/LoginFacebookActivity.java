package com.englishtlu.english_learning.authentication;

import static android.service.controls.ControlsProviderService.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.authentication.model.users;
import com.englishtlu.english_learning.main.MainActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class LoginFacebookActivity extends AppCompatActivity {

    CallbackManager mCallbackManager;
    LoginButton loginButton;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_facebook);
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if(accessToken!=null && !accessToken.isExpired()){
            startActivity(new Intent(LoginFacebookActivity.this,MainActivity.class));
        }
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance("https://e-learning-app-9e5d6-default-rtdb.firebaseio.com/");
        LoginButton loginButton = findViewById(R.id.login_button);

       loginButton.setReadPermissions("email", "public_profile");
       loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @SuppressLint("InlinedApi")
            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }


            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });

    }
    protected void onStart(){
        super.onStart();
        user = mAuth.getCurrentUser();
        if(user!=null){
            Intent intent = new Intent(LoginFacebookActivity.this, MainActivity.class);
            intent.putExtra("name", Objects.requireNonNull(user.getDisplayName()).toString());
            startActivity(intent);
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            user = mAuth.getCurrentUser();
                            assert user != null;
                            users users1 = new users();
                            users1.setUserName(user.getDisplayName());
                            database.getReference().child("users").child(user.getUid()).setValue(users1);
                            Intent intent = new Intent(LoginFacebookActivity.this, MainActivity.class);
                            intent.putExtra("name", Objects.requireNonNull(user.getDisplayName()).toString());
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginFacebookActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}