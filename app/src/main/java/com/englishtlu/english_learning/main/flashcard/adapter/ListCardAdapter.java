package com.englishtlu.english_learning.main.flashcard.adapter;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.englishtlu.english_learning.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ListCardAdapter extends RecyclerView.Adapter<ListCardAdapter.MyViewHolder> {
    public Context context;
    private List<String> cardList;
    private String userId;
    private String nameDesk;
    private DatabaseReference databaseReference;

    public interface OnCardListenner {
        void OnDeleteClicked(int position);

    }

    public ListCardAdapter(Context context, List<String> cardList, String userId, String nameDesk) {
        this.context = context;
        this.cardList = cardList;
        this.userId = userId;
        this.nameDesk = nameDesk;
        this.databaseReference = FirebaseDatabase.getInstance().getReference("Flashcards").child(userId).child(nameDesk);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        carbon.widget.TextView namecard;
        ImageView btnDelete, buttobEdit;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.namecard = itemView.findViewById(R.id.cardName);
            this.btnDelete = itemView.findViewById(R.id.btnDetlte);
            this.buttobEdit = itemView.findViewById(R.id.btnEdit);
        }

        private void setData(final int pos) {
            namecard.setText(cardList.get(pos));
            Toast.makeText(context,cardList.get(pos).toString(),Toast.LENGTH_SHORT).show();

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.alerat_dialog);
                    dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().setBackgroundDrawable(getDrawable(context,R.drawable.dialog_bg));
                    dialog.setCancelable(false);

                    AppCompatButton btnCancelDelete = dialog.findViewById(R.id.buttonCancel);
                    AppCompatButton btnSubmitDelte = dialog.findViewById(R.id.buttonSave);

                    dialog.show();

                    btnCancelDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                    btnSubmitDelte.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String cardName = cardList.get(pos);
                            removeCard(pos);
                            deleteCardFromFirebase(cardName);
                            dialog.dismiss();
                        }
                    });
                }
            });

            buttobEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showEditDialog(pos);
                }
            });
        }
        private void removeCard(int position) {
            cardList.remove(position);
            notifyItemRemoved(position);
            //notifyItemRangeChanged(position, cardList.size());
        }
        private void deleteCardFromFirebase(String cardName) {
            databaseReference.child(cardName).removeValue()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Card deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Failed to delete card", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        private void showEditDialog(final int pos) {
            String cardId = cardList.get(pos);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Flashcards").child(userId).child(nameDesk).child(cardId);
            Dialog addeskDialog = new Dialog(context);
            addeskDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            addeskDialog.setContentView(R.layout.dialog_box_card);
            addeskDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            addeskDialog.getWindow().setBackgroundDrawable(getDrawable(context,R.drawable.dialog_bg));
            addeskDialog.setCancelable(false);

            // Initialize dialog views
            EditText txtQuestion = addeskDialog.findViewById(R.id.editTextQuestion);
            EditText txtAnswer = addeskDialog.findViewById(R.id.editTextAnswer);
            AppCompatButton btnCancelCard = addeskDialog.findViewById(R.id.buttonCancel);
            AppCompatButton btnSubmitCard = addeskDialog.findViewById(R.id.buttonSave);

            addeskDialog.show();

            // Fetch current card details
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String currentQuestion = snapshot.child("question").getValue(String.class);
                        String currentAnswer = snapshot.child("answer").getValue(String.class);

                        txtQuestion.setText(currentQuestion);
                        txtAnswer.setText(currentAnswer);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(context, "Failed to fetch card details", Toast.LENGTH_SHORT).show();
                }
            });

            btnCancelCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addeskDialog.dismiss();
                }
            });

            btnSubmitCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String updatedQuestion = txtQuestion.getText().toString().trim();
                    String updatedAnswer = txtAnswer.getText().toString().trim();

                    if (!updatedQuestion.isEmpty() && !updatedAnswer.isEmpty()) {
                        databaseReference.child("question").setValue(updatedQuestion);
                        databaseReference.child("answer").setValue(updatedAnswer).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    notifyItemChanged(pos);
                                    addeskDialog.dismiss();
                                    Toast.makeText(context, "Card updated successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Failed to update card", Toast.LENGTH_SHORT).show();
                                    addeskDialog.dismiss();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(context, "Question and Answer cannot be empty", Toast.LENGTH_SHORT).show();
                        addeskDialog.dismiss();
                    }
                }
            });

        }
    }
}