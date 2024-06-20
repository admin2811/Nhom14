package com.englishtlu.english_learning.main.profile.adapter;




import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TaskInfo;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.List;


public class DocAdapter extends RecyclerView.Adapter<DocAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onSelectFileButtonClick(int position);
    }
    private List<Doc> docList;

    private Context context;

    private OnItemClickListener listener;
    private DatabaseReference databaseReference;

    public DocAdapter(List<Doc> docList, Context context, OnItemClickListener listener){
        this.docList = docList;
        this.context = context;
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSelectedFileName(String fileName){
        notifyDataSetChanged();
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
        holder.size.setText(formatSize(doc.getPdf().length()));
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
                TextView tvSelectedFile = dialogView.findViewById(R.id.tvSelectedFile);
                Button btnUpload = dialogView.findViewById(R.id.btnUpload);

                AlertDialog dialog = builder.create();
                //Chọn File
                btnSelectedFile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onSelectFileButtonClick(holder.getAdapterPosition());
                    }
                });
                // Hiển thị tên file đã  trong một chỉ mục bất kì
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
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
                                    tvSelectedFile.setText(doc.getName());
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                dialog.show();
            }
        });
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
