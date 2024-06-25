package com.englishtlu.english_learning.main.Grammar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.englishtlu.english_learning.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.function.Consumer;

public class GrammarActivity extends AppCompatActivity {

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_grammar);

        ImageView backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Ends the current activity and returns to the previous one (MainActivity)
            }
        });
        FirebaseApp.initializeApp(this);

        RecyclerView recyclerView = findViewById(R.id.Grammarrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseStorage.getInstance().getReference().child("videos").child("videosgrammar").listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                ArrayList<Grammar> arrayList = new ArrayList<>();
                GrammarAdapter adapter = new GrammarAdapter(GrammarActivity.this, arrayList);
                adapter.setOnItemClickListener(new GrammarAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(Grammar grammar) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(grammar.getUrl()));
                        intent.setDataAndType(Uri.parse(grammar.getUrl()),"video/*");
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(adapter);
                listResult.getItems().forEach(new Consumer<StorageReference>() {
                    @Override
                    public void accept(StorageReference storageReference) {
                        Grammar grammar = new Grammar();
                        grammar.setTitle(storageReference.getName());
                        storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @SuppressLint("NotifyDataSetChanged")
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                String url = "https:" + task.getResult().getEncodedAuthority() + task.getResult().getEncodedPath() + "?alt=media&token=" +
                                        task.getResult().getQueryParameters("token").get(0);
                                grammar.setUrl(url);
                                arrayList.add(grammar);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(GrammarActivity.this,"Failed to retrive videos",Toast.LENGTH_SHORT).show();
            }
        });
    }
}