package com.englishtlu.english_learning.main.flashcard;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.flashcard.adapter.ListDeskAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StartLearnFlashCardActivity extends AppCompatActivity {
    FirebaseAuth auth;
    String userId;
    List<String> desks;
    ListDeskAdapter listDeskAdapter;
    private ImageView btnBack;
    RecyclerView rcListDesk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_start_learn_flash_card);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rcListDesk = findViewById(R.id.listDesk);
        btnBack = findViewById(R.id.btnRecall);

        auth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
        }

        desks = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Flashcards").child(userId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        desks.add(childSnapshot.getKey());
                    }
                    listDeskAdapter = new ListDeskAdapter(StartLearnFlashCardActivity.this, desks);
                    rcListDesk.setAdapter(listDeskAdapter);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(StartLearnFlashCardActivity.this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rcListDesk.setLayoutManager(layoutManager);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}