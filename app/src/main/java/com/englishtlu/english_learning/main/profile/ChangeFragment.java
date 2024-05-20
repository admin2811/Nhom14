package com.englishtlu.english_learning.main.profile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.profile.model.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class ChangeFragment extends Fragment {

    private EditText fullname,mobile,dob,school;
    Spinner spinnerRelative,spinnerGender;
    private ImageView selectPhoto,ProfileImage;
    private Uri imageUri;
    private Bitmap bitmap;
    private FirebaseStorage storage;
    private FirebaseFirestore firestore;
    private StorageReference storageReference;
    private String PhotoUrl;
    private FirebaseAuth firebaseAuth;
    private String currentUserId;
    private String DocId;
    private Button save;
    private ProgressBar progressBar;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change, container, false);
        fullname = view.findViewById(R.id.editTextText);
        mobile = view.findViewById(R.id.editTextText2);
        dob = view.findViewById(R.id.editTextText3);
        school = view.findViewById(R.id.editTextText4);
        spinnerRelative = view.findViewById(R.id.spinner2);
        selectPhoto = view.findViewById(R.id.imageView11);
        ProfileImage = view.findViewById(R.id.imageView10);
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        save = view.findViewById(R.id.edtProfile);
        currentUserId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        progressBar = view.findViewById(R.id.progressBar2);
        selectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckStoragePermission();
                PickImageFromGallery();
            }
        });
        ArrayList<String> relative = new ArrayList<>();
        relative.add("Marry");
        relative.add("Single");
        relative.add("Divorced");
        relative.add("Widowed");
        relative.add("Separated");
        relative.add("In a relationship");
        relative.add("Engaged");
        relative.add("Me");

        ArrayAdapter<String> arrayAdapter =  new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, relative);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRelative.setAdapter(arrayAdapter);

        spinnerGender = view.findViewById(R.id.spinner);
        ArrayList<String> gender = new ArrayList<>();
        gender.add("Male");
        gender.add("Female");
        ArrayAdapter<String> arrayAdapter1 =  new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, gender);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(arrayAdapter1);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                UploadImage();
            }
        });
        return view ;
    }

    private void CheckStoragePermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != getActivity().getPackageManager().PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }else {
                PickImageFromGallery();
            }
        }else {
            PickImageFromGallery();
        }
    }

    private void PickImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(intent);

    }
    ActivityResultLauncher<Intent> launcher
            =registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if(data != null && data.getData() != null){
                        imageUri =data.getData();
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(
                                    getActivity().getContentResolver(),
                                    imageUri
                            );
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if(imageUri != null){
                        ProfileImage.setImageBitmap(bitmap);
                    }
                }
            }
    );
    private void UploadImage(){
        if(imageUri != null){
            final StorageReference reference = storageReference.child("profileImages/"+imageUri.getLastPathSegment());
            reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            if(uri != null){
                                PhotoUrl=uri.toString();
                                UploadProfileInfo();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void UploadProfileInfo(){
        String FullName = fullname.getText().toString();
        String Mobile = mobile.getText().toString();
        String Dob = dob.getText().toString();
        String School = school.getText().toString();
        String Relative = spinnerRelative.getSelectedItem().toString();
        String Gender = spinnerGender.getSelectedItem().toString();
        if(FullName.isEmpty() || Mobile.isEmpty() || Dob.isEmpty() || School.isEmpty() || Relative.isEmpty() || Gender.isEmpty()){
            Toast.makeText(getContext(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
        }else {
            DocumentReference documentReference = firestore.collection("Profile").document();
            Profile profile = new Profile(FullName,Mobile,Dob,Gender,School,Relative,"",currentUserId,PhotoUrl);
            documentReference.set(profile, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        if(task.isSuccessful()){
                            DocId = documentReference.getId();
                            profile.setProfileDocId(DocId);
                            documentReference.set(profile, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getContext(),"Profile Updated",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}