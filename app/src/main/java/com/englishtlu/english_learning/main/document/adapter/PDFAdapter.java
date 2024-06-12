package com.englishtlu.english_learning.main.document.adapter;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.document.model.PDF;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PDFAdapter extends RecyclerView.Adapter<PDFAdapter.PDFViewHolder> {

    private List<PDF> pdfList;
    private Context context;
    public PDFAdapter(List<PDF> pdfList, Context context) {
        this.pdfList = pdfList;
        this.context = context;
    }


    @NonNull
    @Override
    public PDFViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pdf,parent,false);
       return new PDFViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PDFViewHolder holder, int position) {
        PDF pdf = pdfList.get(position);
        holder.name.setText(pdf.getName());
        holder.size.setText(formatSize(pdf.getPdf().length()));
        holder.ivView.setOnClickListener(v -> {
            //Decode base64 string to btye array
            byte[] pdfAsBytes = Base64.decode(pdf.getPdf().getBytes(), Base64.DEFAULT);
            try {
                File tempFile = File.createTempFile("tempPdf",".pdf",context.getCacheDir());
                FileOutputStream fos = new FileOutputStream(tempFile);
                fos.write(pdfAsBytes);
                fos.close();

                Uri pdfUri = FileProvider.getUriForFile(context, context.getPackageName() + ".file-provider", tempFile);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(pdfUri, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                context.startActivity(intent);

                //savePdfToExternalStorage(pdf.getName(), pdfAsBytes);
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        holder.ivDownload.setOnClickListener(v -> {
            byte[] pdfAsBytes = Base64.decode(pdf.getPdf().getBytes(), Base64.DEFAULT);
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    savePdfToExternalStorage(pdf.getName(), pdfAsBytes);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        holder.ivAdd.setOnClickListener(v -> {
            // Save to Firebase Storage and Realtime Database
            byte[] pdfAsBytes = Base64.decode(pdf.getPdf().getBytes(), Base64.DEFAULT);
            try {
                savePdfToFirebaseStorage(pdf.getName(), pdfAsBytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void savePdfToFirebaseStorage(String name, byte[] pdfAsBytes) throws IOException {
        File tempFile = File.createTempFile(name, ".pdf", context.getCacheDir());
        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(pdfAsBytes);
        fos.close();

        Uri fileUri = Uri.fromFile(tempFile);

        if (fileUri != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("pdfs/" + name + ".pdf");

            storageReference.putFile(fileUri)
                    .addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                        String downloadUrl = uri.toString();
                        String pdfId = FirebaseDatabase.getInstance().getReference("pdfs").push().getKey();
                        savePdfToRealtimeDatabase(pdfId, name, downloadUrl);
                    }).addOnFailureListener(e -> {
                        Toast.makeText(context, "Lấy URL thất bại.", Toast.LENGTH_SHORT).show();
                        Log.e("FirebaseStorageError", "Lỗi lấy URL: ", e);
                    }))
                    .addOnFailureListener(e -> {
                        Toast.makeText(context, "Lưu tệp tin PDF thất bại.", Toast.LENGTH_SHORT).show();
                        Log.e("FirebaseStorageError", "Lỗi lưu tệp tin: ", e);
                    });
        } else {
            Toast.makeText(context, "Tạo Uri từ tệp tin thất bại.", Toast.LENGTH_SHORT).show();
            Log.e("UriError", "Tạo Uri từ tệp tin thất bại.");
        }
    }

    private void savePdfToRealtimeDatabase(String id, String name, String downloadUrl) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("pdfs");
        PDF pdf = new PDF(id, name, downloadUrl);
        if (id != null) {
            databaseReference.child(id).setValue(pdf)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(context, "Lưu PDF thành công.", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(context, "Lưu PDF thất bại.", Toast.LENGTH_SHORT).show();
                        Log.e("FirebaseDatabaseError", "Lỗi lưu PDF: ", e);
                    });
        } else {
            Toast.makeText(context, "Không thể tạo ID cho PDF.", Toast.LENGTH_SHORT).show();
        }
    }
    @SuppressLint("QueryPermissionsNeeded")
        private void savePdfToExternalStorage(String name, byte[] pdfAsBytes) throws IOException {
            // Lưu tệp PDF vào bộ nhớ ngoài
            File pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), name + ".pdf");
            FileOutputStream fos = new FileOutputStream(pdfFile);
            fos.write(pdfAsBytes);
            fos.close();
            Toast.makeText(context, "PDF đã được tải xuống thành công.", Toast.LENGTH_SHORT).show();

            showNotification(context, "Download hoàn thành", "PDF đã được tải xuống thành công.");
    
        }


    @SuppressLint("DefaultLocale")
    private String formatSize(long size) {
        if (size <= 0) return "0 B";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return String.format("%.2f %s", size / Math.pow(1024, digitGroups), units[digitGroups]);
    }
    private void showNotification(Context context, String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default")
                .setSmallIcon(R.drawable.baseline_file_download_24)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(1, builder.build());
        }
    }

    @Override
    public int getItemCount() {
        return pdfList.size();
    }

    public static class PDFViewHolder extends RecyclerView.ViewHolder {
        TextView name,size;
        ImageView ivView,ivDownload,ivAdd;
        public PDFViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvPdfName);
            size = itemView.findViewById(R.id.tvPdfSize);
            ivView = itemView.findViewById(R.id.ivView);
            ivDownload = itemView.findViewById(R.id.ivDownload);
            ivAdd = itemView.findViewById(R.id.ivAdd);
        }
    }
}
