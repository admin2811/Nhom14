package com.englishtlu.english_learning.main.profile;

import static com.englishtlu.english_learning.R.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.R.id;
import com.englishtlu.english_learning.authentication.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class ProfileFragment extends Fragment {
    private FirebaseFirestore firestore;

    private DatabaseReference databaseReference;
    private String currentUserId;

    private ProgressBar progressBar;
    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(layout.fragment_profile, container, false);
        ConstraintLayout myProfile = (ConstraintLayout) view.findViewById(R.id.myProfile);
        ConstraintLayout FAQ = (ConstraintLayout) view.findViewById(id.FAQ);
        ConstraintLayout share = (ConstraintLayout) view.findViewById(id.share);
        ConstraintLayout changePassword = (ConstraintLayout) view.findViewById(id.changePasswordandEmail);
        ConstraintLayout signOut = (ConstraintLayout) view.findViewById(id.signOut);
        final String app = requireActivity().getPackageName();
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar5);
        progressBar.setVisibility(View.VISIBLE);
        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(id.fragmentContainerView, new MyProfileFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        FAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(id.fragmentContainerView,new FAQFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "Check this App\n" + "https://play.google.com/store/apps/details?id="+app);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "Share via"));
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(id.fragmentContainerView,new ChangePasswordandEmailFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(requireContext(), LoginActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });
        firestore = FirebaseFirestore.getInstance();
        //lấy id hiện tai cua nguoi dung
        FirebaseAuth auth = FirebaseAuth.getInstance();
        currentUserId = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        displayUserInfo(view);
        return view;
    }

    private void displayUserInfo(View view) {
        DocumentReference docRef = firestore.collection("Profile").document(currentUserId);
        progressBar.setVisibility(View.VISIBLE);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String fullName = documentSnapshot.getString("fullname");
                    String photoUrl = documentSnapshot.getString("profileImage");
                    // Lấy email từ Realtime Database
                    DatabaseReference emailRef = FirebaseDatabase.getInstance().getReference("Users");
                    emailRef.child(currentUserId).child("textEmail").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                String email = dataSnapshot.getValue(String.class);
                                // hiển thị thông tin người dùng
                                TextView fullNameEdt = view.findViewById(id.fullNameTxt);
                                TextView emailEdt = view.findViewById(id.emailTxt);
                                ImageView profileImage = view.findViewById(id.profile_Image);
                                if (photoUrl != null && !photoUrl.isEmpty()) {
                                    Glide.with(requireContext())
                                            .load(photoUrl)
                                            .apply(RequestOptions.circleCropTransform())
                                            .placeholder(R.drawable.baseline_account_circle_24)
                                            .into(profileImage);
                                } else {
                                    // Xử lý khi không có ảnh
                                    Toast.makeText(requireContext(), "No Image", Toast.LENGTH_SHORT).show();
                                }
                                fullNameEdt.setText(fullName);
                                emailEdt.setText(email); // Set the email
                                progressBar.setVisibility(View.GONE);
                            } else {
                                // nếu không có email
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(requireContext(), "No Email", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // nếu không lấy được email
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(requireContext(), "Error getting email!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // nếu không có thông tin người dùng
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "No Profile", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // nếu không lấy được thông tin người dùng
                progressBar.setVisibility(View.GONE);
                Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}