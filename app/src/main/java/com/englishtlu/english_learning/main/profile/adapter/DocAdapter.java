package com.englishtlu.english_learning.main.profile.adapter;




import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TaskInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.profile.model.Doc;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class DocAdapter extends RecyclerView.Adapter<DocAdapter.ViewHolder> implements Filterable {


    private List<Doc> docList;
    private List<Doc> docListOld;

    private Context context;


    private DatabaseReference databaseReference;

    private boolean isSelectFile = false;
    private Uri selectedPdfUri;
    private FirebaseStorage firebaseStorage;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()){
                    docList = docListOld;
                }else {
                     List<Doc> list = new ArrayList<>();
                     for(Doc doc : docListOld){
                         if(doc.getName().toLowerCase().contains(strSearch.toLowerCase())){
                             list.add(doc);
                         }
                     }
                        docList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = docList;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                docList = (List<Doc>) results.values;
                notifyDataSetChanged();

            }
        };
    }


    public interface OnItemClickListener {

        void onSelectFileButtonClick(int position, TextView tvSelectedFile);
    }
    private final OnItemClickListener listener;
    public DocAdapter(List<Doc> docList, Context context,OnItemClickListener listener){
        this.docList = docList;
        this.context = context;
        this.listener = listener;
        this.docListOld = docList;
        firebaseStorage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("document");

    }

    public void setSelectFile(Uri uri){
        selectedPdfUri = uri;
    }
    @NonNull
    @Override
    public DocAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doc,parent,false);
        return new ViewHolder(view);
    }
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull DocAdapter.ViewHolder holder,  int position) {
        Doc doc = docList.get(position);
        holder.name.setText(doc.getName());
        holder.size.setText(formatSize(doc.getPdf().length()))
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Xem pdf
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(doc.getPdf()), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                context.startActivity(intent);
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Doc selectedDoc = docList.get(holder.getAdapterPosition());
                new AlertDialog.Builder(context)
                        .setTitle("Delete PDF")
                        .setMessage("Are you sure you want to delete this PDF?")
                        .setPositiveButton("Yes", (dialog, which) -> deleteFileFromFirebase(selectedDoc.getId(), holder.getAdapterPosition()))
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin của mục đang được click
                Doc selecteDoc = docList.get(holder.getAdapterPosition());

                // Tạo dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Edit PDF");

                // Gắn layout cho dialog
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.dialog_upload_pdf, null);
                builder.setView(dialogView);

                // Ánh xạ các thành phần trong layout của dialog
                EditText etPdfName = dialogView.findViewById(R.id.etPdfName);
                Button btnSelectedFile = dialogView.findViewById(R.id.btnSelectFile);
                final TextView tvSelectedFile = dialogView.findViewById(R.id.tvSelectedFile);
                Button btnEdit = dialogView.findViewById(R.id.btnUpload);

                AlertDialog dialog = builder.create();
                //Chọn File
                btnSelectedFile.setOnClickListener(v1 -> {
                    int pos = holder.getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        listener.onSelectFileButtonClick(pos, tvSelectedFile);
                    }
                });
                // Hiển thị tên file đã  trong một chỉ mục bất kì
                etPdfName.setText(selecteDoc.getName());
                tvSelectedFile.setText(selecteDoc.getName());
                // Upload file
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pdfName = etPdfName.getText().toString().trim();
                        String oldName = selecteDoc.getName();
                        if(pdfName.isEmpty()){
                            Toast.makeText(context, "Please enter PDF name", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(selectedPdfUri == null){
                            Toast.makeText(context, "Please select a PDF file", Toast.LENGTH_SHORT).show();
                            Log.e("DocAdapter", "selectedPdfUri is null");
                            return;
                        }
                        editFileToFireBase(selecteDoc.getId(),oldName,pdfName,selectedPdfUri);
                        dialog.dismiss();

                    }
                });
              /*  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){
                    String uid = user.getUid();
                    databaseReference = FirebaseDatabase.getInstance().getReference("document").child(uid).child("pdfs").child(selecteDoc.getId());
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                Doc doc = snapshot.getValue(Doc.class);
                                if(doc != null){
                                    etPdfName.setText(doc.getName());
                                    // Hiển thị tên file đã chọn
                                    tvSelectedFile[0].setText(doc.getName());
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }*/
                dialog.show();
            }
        });
    }


    private void deleteFileFromFirebase(String id, int position) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(context, "User is not logged in", Toast.LENGTH_SHORT).show();
            return;
        }
        String uid = user.getUid();
        String pdfName = docList.get(position).getName();
        StorageReference storageReference = firebaseStorage.getReference().child("pdfs/" + uid + "/" + pdfName + ".pdf");
        Log.d("DeleteFile", "Deleting file at: pdfs/" + uid + "/" + pdfName + ".pdf");
        // Delete file from Firebase Storage
        storageReference.delete()
                .addOnSuccessListener(aVoid -> {
                    // On successful deletion from storage, delete from database
                    Log.d("DeleteFile", "File deleted from storage. Now deleting from database.");
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("document").child(uid).child("pdfs").child(id);
                    databaseReference.removeValue()
                            .addOnSuccessListener(aVoid1 -> {
                                if (position < docList.size()) {
                                    docList.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, docList.size());
                                    Toast.makeText(context, "File deleted successfully", Toast.LENGTH_SHORT).show();
                                    Log.d("DeleteFile", "File and database record deleted successfully.");
                                } else {
                                    Log.e("DeleteFile", "Invalid position to remove: " + position);
                                }
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(context, "Failed to delete file from database", Toast.LENGTH_SHORT).show();
                                Log.e("FirebaseDatabaseError", "Error deleting file from database", e);
                            });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to delete file from storage", Toast.LENGTH_SHORT).show();
                    Log.e("FirebaseStorageError", "Error deleting file from storage", e);
                });
    }

    private void editFileToFireBase(String id, String oldName, String pdfName, Uri fileUri) {
        if (fileUri != null) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                String uid = user.getUid();
                String pdfName1 = pdfName + ".pdf";
                StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://e-learning-app-9e5d6.appspot.com").child("pdfs/" + uid + "/" + pdfName1);
                //Kiểm tra xem oldName có đuôi pdf hay không
                if (!oldName.contains(".pdf")) {
                    oldName = oldName + ".pdf";
                }
                StorageReference oldStorageReference = firebaseStorage.getReference().child("pdfs/" + uid + "/" + oldName);

                // Delete old file
                String finalOldName = oldName;
                oldStorageReference.delete()
                        .addOnSuccessListener(aVoid -> {
                            Log.d("EditFile", "Successfully deleted old file at: pdfs/" + uid + "/" + finalOldName);
                            // Upload new file
                            storageReference.putFile(fileUri)
                                    .addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                                        String downloadUrl = uri.toString();
                                        databaseReference = FirebaseDatabase.getInstance().getReference("document").child(uid).child("pdfs").child(id);
                                        Doc newDoc = new Doc(id, pdfName, downloadUrl);

                                        databaseReference.setValue(newDoc)
                                                .addOnSuccessListener(bVoid -> {
                                                    Toast.makeText(context, "File updated successfully", Toast.LENGTH_SHORT).show();
                                                })
                                                .addOnFailureListener(e -> {
                                                    Toast.makeText(context, "Failed to save file URL", Toast.LENGTH_SHORT).show();
                                                    Log.e("FirebaseDatabaseError", "Error saving file URL", e);
                                                });
                                    }).addOnFailureListener(e -> {
                                        Toast.makeText(context, "Failed to get download URL", Toast.LENGTH_SHORT).show();
                                        Log.e("FirebaseStorageError", "Error getting download URL", e);
                                    }))
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(context, "Failed to upload file", Toast.LENGTH_SHORT).show();
                                        Log.e("FirebaseStorageError", "Error uploading file", e);
                                    });
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(context, "Failed to delete old file", Toast.LENGTH_SHORT).show();
                            Log.e("FirebaseStorageError", "Error deleting old file at: pdfs/" + uid + "/" + finalOldName + ".pdf", e);
                        });
            } else {
                Toast.makeText(context, "User not authenticated", Toast.LENGTH_SHORT).show();
                Log.e("AuthenticationError", "User not authenticated");
            }
        } else {
            Toast.makeText(context, "File URI is null", Toast.LENGTH_SHORT).show();
            Log.e("FileError", "File URI is null");
        }
    }




    private void editFileToRealtimeDatabase(String id, String pdfName, String downloadUrl) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            String uid = user.getUid();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("document").child(uid).child("pdfs").child(id);
            Doc newDoc = new Doc(id, pdfName, downloadUrl);
            databaseReference.setValue(newDoc)
                    .addOnSuccessListener(aVoid -> Toast.makeText(context, "File updated successfully", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> {
                        Toast.makeText(context, "Failed to save file Url", Toast.LENGTH_SHORT).show();
                        Log.e("FirebaseDatabaseError", "Error saving file Url", e);
                    });
        }
    }

    @SuppressLint("DefaultLocale")
    private String formatSize(long size) {
        if (size <= 0) return "0 B";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return String.format("%.2f %s", size / Math.pow(1024, digitGroups), units[digitGroups]);
    }
    @Override
    public int getItemCount() {
        return docList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, size;

        ImageView ivDelete , ivEdit;
        @SuppressLint("CutPasteId")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvPdfName);
            size = itemView.findViewById(R.id.tvPdfSize);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            ivEdit = itemView.findViewById(R.id.ivEdit);
        }
    }
}
