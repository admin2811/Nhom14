package com.englishtlu.english_learning.main.flashcard;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.flashcard.adapter.ListCardAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

public class CardManageActivity extends AppCompatActivity {
    FrameLayout addCradFrame;
    Dialog addcarddialog;
    Dialog addeskDialog;
    Dialog confirmDelete;
    RecyclerView rcCardList;
    ListCardAdapter listCardAdapter;
    AppCompatButton addDeskbtn, addCardbtn, deleteDeskbtn;
    FirebaseAuth auth;
    String userId;
    EditText txtQuestion, txtAnswer, txtDesk;
    AppCompatButton btnCancelCard,btnCancelDesk, btnSubmitCard, btnSubmitDesk, btnSubmitDelete, btnCancelDelete;
    String nameDesk;
    Spinner choiceDeks;
    List<String> desks;
    List<String> cards;
    int lenDesk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_card_manage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addDeskbtn = findViewById(R.id.buttonAddesk);
        addCardbtn = findViewById(R.id.buttonAddCard);
        choiceDeks = findViewById(R.id.spinnerDesks);
        rcCardList = findViewById(R.id.cardList);
        deleteDeskbtn = findViewById(R.id.buttonDeleteDesk);

        auth = FirebaseAuth.getInstance();

        setDeskDialog();
        setCarddialog();
        setDeleteDialog();

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
        }

        desks = new ArrayList<>();
        cards = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Flashcards").child(userId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        desks.add(childSnapshot.getKey());
                    }

                    ArrayAdapter arrayAdapter = new ArrayAdapter<>(CardManageActivity.this, android.R.layout.simple_spinner_item, desks);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    choiceDeks.setAdapter(arrayAdapter);

                    // Set a default value for Spinner
                    if (!desks.isEmpty()) {
                        choiceDeks.setSelection(0);
                        nameDesk = desks.get(0);

                        loadCardsForDesk(nameDesk);
                    }

                    choiceDeks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            nameDesk = parent.getItemAtPosition(position).toString();  // Update the nameDesk variable
                            if (nameDesk != null && !nameDesk.isEmpty()) {
                                loadCardsForDesk(nameDesk);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });

        addDeskbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addeskDialog.show();
            }
        });

        btnCancelDesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addeskDialog.cancel();
            }
        });

        addCardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addcarddialog.show();
            }
        });

        btnCancelCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addcarddialog.cancel();
            }
        });

        deleteDeskbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedDesk();
            }
        });

        btnCancelDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete.cancel();
            }
        });

        submitDesk();
        submitCard();
    }

    private void loadCardsForDesk(String deskName) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Flashcards").child(userId).child(deskName);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    cards.clear();
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        cards.add(childSnapshot.getKey());
                    }
                    lenDesk = cards.size();
                    cards.remove(lenDesk-1);

                    listCardAdapter = new ListCardAdapter(CardManageActivity.this, cards, userId, deskName);
                    rcCardList.setAdapter(listCardAdapter);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(CardManageActivity.this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rcCardList.setLayoutManager(layoutManager);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }
    private void setCarddialog(){
        addcarddialog = new Dialog(this);
        addcarddialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addcarddialog.setContentView(R.layout.dialog_box_card);
        addcarddialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        addcarddialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_bg));
        addcarddialog.setCancelable(false);

        txtQuestion = addcarddialog.findViewById(R.id.editTextQuestion);
        txtAnswer = addcarddialog.findViewById(R.id.editTextAnswer);
        btnSubmitCard = addcarddialog.findViewById(R.id.buttonSave);
        btnCancelCard = addcarddialog.findViewById(R.id.buttonCancel);
    }
    private void setDeskDialog(){
        addeskDialog = new Dialog(this);
        addeskDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        addeskDialog.setContentView(R.layout.dialog_box_desk);
        addeskDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        addeskDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_bg));
        addeskDialog.setCancelable(false);

        txtDesk = addeskDialog.findViewById(R.id.editNameDesk);
        btnSubmitDesk = addeskDialog.findViewById(R.id.btn_save);
        btnCancelDesk = addeskDialog.findViewById(R.id.btn_cancel);
    }
    private void setDeleteDialog(){
        confirmDelete = new Dialog(this);
        confirmDelete.requestWindowFeature(Window.FEATURE_NO_TITLE);
        confirmDelete.setContentView(R.layout.alerat_dialog);
        confirmDelete.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        confirmDelete.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_bg));
        addeskDialog.setCancelable(false);

        btnSubmitDelete = confirmDelete.findViewById(R.id.buttonSave);
        btnCancelDelete = confirmDelete.findViewById(R.id.buttonCancel);
    }
    private void submitDesk(){
        btnSubmitDesk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameDesk = txtDesk.getText().toString();
                if(!nameDesk.isEmpty()){
                    if (desks.contains(nameDesk)){
                        Toast.makeText(CardManageActivity.this, "Desk name already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Flashcards").child(userId).child(nameDesk);
                        databaseReference.child("inital").setValue("inital")
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            desks.add(nameDesk);

                                            // Cập nhật Spinner
                                            ArrayAdapter arrayAdapter = new ArrayAdapter<>(CardManageActivity.this, android.R.layout.simple_spinner_item, desks);
                                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            choiceDeks.setAdapter(arrayAdapter);

                                            // Đặt Spinner về desk mới
                                            choiceDeks.setSelection(desks.size() - 1);

                                            // Đóng Dialog
                                            addeskDialog.dismiss();
                                            txtDesk.setText("");

                                            Toast.makeText(CardManageActivity.this, "Desk added successfully", Toast.LENGTH_SHORT).show();
                                            confirmDelete.dismiss();
                                        } else {
                                            Toast.makeText(CardManageActivity.this, "Failed to add desk", Toast.LENGTH_SHORT).show();
                                            confirmDelete.dismiss();
                                        }
                                    }
                                });
                    }
                } else {
                    Toast.makeText(CardManageActivity.this, "Desk name cannot be empty", Toast.LENGTH_SHORT).show();
                    confirmDelete.dismiss();
                }
            }
        });
    }
    private void submitCard(){
        Date currentDate = new Date();
        int hour = currentDate.getHours();
        int minute = currentDate.getMinutes();
        int second = currentDate.getSeconds();
        btnSubmitCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = txtQuestion.getText().toString();
                String answer = txtAnswer.getText().toString();
                if (!question.isEmpty() && !answer.isEmpty() && nameDesk != null){
                    Map<String, Object> values = new HashMap<>();
                    values.put("question", question);
                    values.put("answer", answer);

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Flashcards").child(userId).child(nameDesk).child("Card "+lenDesk + " - " + Integer.toString(hour) + " - " + Integer.toString(minute) + " - " + Integer.toString(second));
                    databaseReference.updateChildren(values)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        cards.add("Card "+lenDesk + " - " + Integer.toString(hour) + " - " + Integer.toString(minute) + " - " + Integer.toString(second));
                                        listCardAdapter.notifyDataSetChanged();

                                        // Đóng Dialog
                                        addcarddialog.dismiss();
                                        txtQuestion.setText("");
                                        txtAnswer.setText("");

                                        Toast.makeText(CardManageActivity.this, "Card added successfully", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(CardManageActivity.this, "Failed to add card", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(CardManageActivity.this, "Question and Answer cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void deleteSelectedDesk() {
        confirmDelete.show();
        btnSubmitDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameDesk != null && !nameDesk.isEmpty()) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Flashcards").child(userId).child(nameDesk);
                    databaseReference.removeValue().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Remove the desk from the local list
                            desks.remove(nameDesk);

                            // Update the Spinner
                            ArrayAdapter arrayAdapter = new ArrayAdapter<>(CardManageActivity.this, android.R.layout.simple_spinner_item, desks);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            choiceDeks.setAdapter(arrayAdapter);

                            // Set a new default value if there are remaining desks
                            if (!desks.isEmpty()) {
                                choiceDeks.setSelection(0);
                                nameDesk = desks.get(0);
                                loadCardsForDesk(nameDesk);
                            } else {
                                nameDesk = null;
                                cards.clear();
                                listCardAdapter.notifyDataSetChanged();
                            }

                            Toast.makeText(CardManageActivity.this, "Desk deleted successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CardManageActivity.this, "Failed to delete desk", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(CardManageActivity.this, "No desk selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}