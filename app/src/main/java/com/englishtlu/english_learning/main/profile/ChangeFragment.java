package com.englishtlu.english_learning.main.profile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.profile.model.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class ChangeFragment extends Fragment {

    private EditText fullname,mobile,dob,school;
    Spinner spinnerRelative,spinnerGender;
    private  ImageView goback;
    private ImageView selectPhoto ,ProfileImage;
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
    private static final String TAG = "ChangeFragment";
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change, container, false);
        fullname = view.findViewById(R.id.edtName);
        mobile = view.findViewById(R.id.edtMobile);
        dob = view.findViewById(R.id.edtDob);
        school = view.findViewById(R.id.edtSchool);
        spinnerRelative = view.findViewById(R.id.edtRelative);
        selectPhoto = view.findViewById(R.id.selectPhoto);
        ProfileImage = view.findViewById(R.id.ImageProfile);
        firestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        save = view.findViewById(R.id.saveBtn);
        currentUserId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        progressBar = view.findViewById(R.id.progressBar2);
        goback = view.findViewById(R.id.imageView8);

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });
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

        spinnerGender = view.findViewById(R.id.edtGender);
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
        setProfileUser(view);
        return view ;
    }

    private void setProfileUser(View view) {
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
                    Spinner genderEdt = view.findViewById(R.id.edtGender);
                    EditText mobileEdt = view.findViewById(R.id.edtMobile);
                    Spinner relativeEdt = view.findViewById(R.id.edtRelative);
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
                    mobileEdt.setText(mobile);
                    dobEdt.setText(dob);
                    schoolEdt.setText(school);
                    setSpinnerValue(genderEdt, gender);
                    setSpinnerValue(relativeEdt, relative);
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

    private void setSpinnerValue(Spinner spinner, String value) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
        if (adapter != null) {
            int position = adapter.getPosition(value);
            spinner.setSelection(position);
        }
    }
    @SuppressLint("ObsoleteSdkInt")
    private void CheckStoragePermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if( ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(requireActivity(), new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }else {
                PickImageFromGallery();
            }
        }else {
            PickImageFromGallery();
        }
    }

    private void PickImageFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");

        Intent cameraIntent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Intent chooser = Intent.createChooser(galleryIntent,"Choose Image");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});
        launcher.launch(chooser);

    }
    ActivityResultLauncher<Intent> launcher
            =registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if(data != null){
                        if(data.getData() != null) {
                            imageUri = data.getData();
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(
                                        requireActivity().getContentResolver(),
                                        imageUri
                                );
                            } catch (IOException e) {
                                Log.e(TAG, "Error getting image from gallery", e);
                                Toast.makeText(getContext(), "Error getting image from gallery", Toast.LENGTH_SHORT).show();
                                throw new RuntimeException(e);
                            }
                        }else{
                            Bundle extras = data.getExtras();
                            if (extras != null && extras.get("data") != null) {
                                bitmap = (Bitmap) extras.get("data");
                                // Tạo Uri từ Bitmap và gán cho imageUri
                                imageUri = getImageUri(requireContext(), bitmap);
                            }
                        }
                    }
                    if(bitmap != null){
                        ProfileImage.setImageBitmap(bitmap);
                    }
                }
            }
    );
    // Hàm chuyển đổi Bitmap thành Uri
    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }
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
                            Log.e(TAG, "Failed to get download URL", e);
                            Toast.makeText(getContext(), "Failed to get download URL", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }else{
           Toast.makeText(getContext(),"Please select an image",Toast.LENGTH_SHORT).show();
           progressBar.setVisibility(View.GONE);
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
            progressBar.setVisibility(View.GONE);
        }else {
            DocumentReference documentReference = firestore.collection("Profile").document(currentUserId);
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
                                        //Chuyển về trang Profile
                                        requireActivity().onBackPressed();
                                    }else{
                                        Log.e(TAG, "Failed to update profile document", task.getException());
                                        Toast.makeText(getContext(), "Failed to update profile document", Toast.LENGTH_SHORT).show();
                                    }
                                    progressBar.setVisibility(View.GONE);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e(TAG, "Failed to update profile document", task.getException());
                                    Toast.makeText(getContext(), "Failed to update profile document", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            Log.e(TAG, "Failed to set profile", task.getException());
                            Toast.makeText(getContext(), "Failed to set profile", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG, "Failed to set profile", e);
                    Toast.makeText(getContext(), "Failed to set profile", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}