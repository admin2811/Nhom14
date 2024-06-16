package com.englishtlu.english_learning.main.profile;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.englishtlu.english_learning.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;


public class MyProfileFragment extends Fragment {

    private static final String TAG = "MyProfileFragment";
    private FirebaseFirestore firestore;
    private String currentUserId;
    private ProgressBar progressBar;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        Button editProfile = (Button) view.findViewById(R.id.saveBtn);
        ImageView goBack = (ImageView) view.findViewById(R.id.imageView8);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar4);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, new ProfileFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, new ChangeFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        firestore = FirebaseFirestore.getInstance();
        //lấy id hiện tai cua nguoi dung
        FirebaseAuth auth = FirebaseAuth.getInstance();
        currentUserId = Objects.requireNonNull(auth.getCurrentUser()).getUid();

        displayProfile(view);

        return view;
    }

    private void displayProfile(View view) {
        DocumentReference docRef = firestore.collection("Profile").document(currentUserId);

        progressBar.setVisibility(View.VISIBLE);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {

            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String fullName = documentSnapshot.getString("fullname");
                    String gender = documentSnapshot.getString("gender");
                    String mobile = documentSnapshot.getString("mobile");
                    String relative = documentSnapshot.getString("relative");
                    String dob = documentSnapshot.getString("dob");
                    String school = documentSnapshot.getString("school");
                    String photoUrl = documentSnapshot.getString("profileImage");

                    //hiển thị thông tin người dùng
                    EditText fullNameEdt = view.findViewById(R.id.edtName);
                    EditText genderEdt = view.findViewById(R.id.edtGender);
                    EditText mobileEdt = view.findViewById(R.id.edtMobile);
                    EditText relativeEdt = view.findViewById(R.id.edtRelative);
                    EditText dobEdt = view.findViewById(R.id.edtDob);
                    EditText schoolEdt = view.findViewById(R.id.edtSchool);
                    ImageView profileImage = view.findViewById(R.id.ImageProfile);
                    if(photoUrl != null && !photoUrl.isEmpty()) {
                        Glide.with(requireContext())
                                .load(photoUrl)
                                .apply(RequestOptions.circleCropTransform())
                                .placeholder(R.drawable.baseline_account_circle_24)
                                .into(profileImage);
                    }else{
                        //Xử lý khi không co ảnh
                        Toast.makeText(requireContext(), "No Image", Toast.LENGTH_SHORT).show();

                    }
                        fullNameEdt.setText(fullName);
                        genderEdt.setText(gender);
                        mobileEdt.setText(mobile);
                        relativeEdt.setText(relative);
                        dobEdt.setText(dob);
                        schoolEdt.setText(school);
                        progressBar.setVisibility(View.GONE);

                }else{
                    //nếu không có thông tin người dùng
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(requireContext(), "No Profile", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //nếu không lấy được thông tin người dùng
                Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}