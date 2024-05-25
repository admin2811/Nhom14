package com.englishtlu.english_learning.main.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.authentication.LoginActivity;
import com.englishtlu.english_learning.authentication.utility.PreferenceManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class ChangePasswordandEmailFragment extends Fragment {

    private ImageView backBtn, passwordIcon, passwordConIcon, passwordNewIcon;
    private boolean passwordShowing = false;
    private EditText edtPassword, edtConPassword, edtNewPassword,edtEmail, edtNewEmail;
    AppCompatButton changePassword, changeEmail;
    String password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_passwordand_email, container, false);
        backBtn = view.findViewById(R.id.back);
        passwordIcon = view.findViewById(R.id.iconshow1);
        passwordConIcon = view.findViewById(R.id.iconshow3);
        passwordNewIcon = view.findViewById(R.id.iconshow2);
        edtPassword = view.findViewById(R.id.edtPassword1);
        edtNewPassword = view.findViewById(R.id.edtNewPassword);
        edtConPassword = view.findViewById(R.id.edtConPassword);
        changePassword = view.findViewById(R.id.btnSetPassword);
        edtEmail = view.findViewById(R.id.edtEmailAddress);
        edtNewEmail = view.findViewById(R.id.edtNewEmail);
        changeEmail = view.findViewById(R.id.btnSetEmail);

        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordShowing){
                    passwordShowing = false;

                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.baseline_visibility_off_24);
                }else{
                    passwordShowing = true;
                    edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.baseline_visibility_24);

                }
                edtPassword.setSelection(edtPassword.length());
            }
        });

        passwordNewIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordShowing){
                    passwordShowing = false;

                    edtNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordNewIcon.setImageResource(R.drawable.baseline_visibility_off_24);
                }else{
                    passwordShowing = true;
                    edtNewPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordNewIcon.setImageResource(R.drawable.baseline_visibility_24);

                }
                edtNewPassword.setSelection(edtNewPassword.length());
            }
        });

        passwordConIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordShowing){
                    passwordShowing = false;
                    edtConPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordConIcon.setImageResource(R.drawable.baseline_visibility_off_24);
                }else{
                    passwordShowing = true;
                    edtConPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordConIcon.setImageResource(R.drawable.baseline_visibility_24);

                }
                edtConPassword.setSelection(edtConPassword.length());
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpass = edtPassword.getText().toString();
                String newpass = edtNewPassword.getText().toString();
                String conpass = edtConPassword.getText().toString();

                if(oldpass.isEmpty()){
                    edtPassword.setError("Old Password is required");
                    edtPassword.requestFocus();
                }else if(newpass.isEmpty()){
                    edtNewPassword.setError("New Password is required");
                    edtNewPassword.requestFocus();
                }else if (conpass.isEmpty()) {
                    edtConPassword.setError("Confirm Password is required");
                    edtConPassword.requestFocus();
                } else if (!newpass.equals(conpass)) {
                    edtConPassword.setError("Password do not match");
                    edtConPassword.requestFocus();
                } else {
                    //Change Password
                    UpdatePassword(oldpass,newpass);
                }
            }
        });
        changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldEmail = edtEmail.getText().toString();
                String newEmail = edtNewEmail.getText().toString();
                if(oldEmail.isEmpty()){
                    edtEmail.setError("Old Email is required");
                    edtEmail.requestFocus();
                }else if(newEmail.isEmpty()){
                    edtNewEmail.setError("New Email is required");
                    edtNewEmail.requestFocus();
                }else if(isValidEmail(newEmail)){
                    edtNewEmail.setError("Invalid Email");
                    edtNewEmail.requestFocus();
                }else{
                    //Change Email
                    UpdateEmail(newEmail);
                }
            }
        });
        return view;
    }

    private void UpdateEmail(String newEmail) {
        newEmail = edtNewEmail.getText().toString();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            //Cập nhập email sử dụng verifyBeforeUpdateEmail
            String finalNewEmail = newEmail;
            user.verifyBeforeUpdateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(requireContext(), "Email Updated", Toast.LENGTH_SHORT).show();
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                                .child(user.getUid())
                                .child("textEmail");
                        databaseReference.setValue(finalNewEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(requireContext(),"Please verify your new email to keep login",Toast.LENGTH_SHORT).show();
                                    FirebaseAuth.getInstance().signOut();
                                    //Chuyển đến trang đăng nhập
                                    Intent intent = new Intent(requireContext(), LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    requireActivity().finish();
                                }else{
                                    Toast.makeText(requireContext(), "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(requireContext(), "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if( e instanceof FirebaseAuthInvalidCredentialsException){
                        Toast.makeText(requireContext(), "Invalid Email", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(requireContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
    private String getPassword(){
        PreferenceManager preferenceManager = new PreferenceManager(requireContext());
        return preferenceManager.getPassword();
    }

    private boolean isValidEmail(String oldEmail) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return !oldEmail.matches(emailPattern);
    }

    private void UpdatePassword(String oldpass, String newpass) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            AuthCredential credential = EmailAuthProvider.getCredential(Objects.requireNonNull(user.getEmail()),oldpass);
            user.reauthenticate(credential).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    user.updatePassword(newpass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(requireContext(), "Password Updated", Toast.LENGTH_SHORT).show();
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                                    .child(user.getUid())
                                    .child("textPassword");
                            databaseReference.setValue(newpass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(requireContext(),"Please re-login to continue",Toast.LENGTH_SHORT).show();
                                        FirebaseAuth.getInstance().signOut();
                                        //Chuyển đến trang đăng nhập
                                        Intent intent = new Intent(requireContext(), LoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        requireActivity().finish();
                                    }else{
                                        Toast.makeText(requireContext(), "Error: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(requireContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    if( e instanceof FirebaseAuthInvalidCredentialsException){
                        Toast.makeText(requireContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(requireContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}