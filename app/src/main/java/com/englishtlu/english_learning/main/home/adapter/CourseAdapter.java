package com.englishtlu.english_learning.main.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.analysis.AnalysisActivity;
import com.englishtlu.english_learning.main.flashcard.FlashCardActivity;
import com.englishtlu.english_learning.main.home.model.Course;
import com.englishtlu.english_learning.main.test.CourseActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{
    ArrayList<Course> items;
    private ArrayList<Course> itemsFiltered;
    Context context;
    public  CourseAdapter(Context context, ArrayList<Course> items) {
        this.context = context;
        this.items = items;
        this.itemsFiltered = new ArrayList<>(items);
    }
    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_course,parent,false);
        context = parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course item = itemsFiltered.get(position);
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        holder.star.setText(new DecimalFormat("##.#").format(item.getStar()));
        @SuppressLint("DiscouragedApi") int drawableResourceId = context.getResources().getIdentifier(item.getPicpath(), "drawable", context.getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.picpath);
        holder.btnChanglen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsFiltered.size();
    }

    public void filterList(String text) {
        itemsFiltered.clear();
        if (TextUtils.isEmpty(text)) {
            itemsFiltered.addAll(items); // Nếu không có từ khóa, hiển thị toàn bộ danh sách
        } else {
            String pattern = text.toLowerCase().trim();
            for (Course item : items) {
                if (item.getTitle().toLowerCase().contains(pattern)) {
                    itemsFiltered.add(item); // Thêm vào danh sách lọc nếu tiêu đề chứa từ khóa
                }
            }
        }
        notifyDataSetChanged(); // Thông báo cho RecyclerView là đã thay đổi dữ liệu
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title, description, star;

        ImageView picpath;
        Button btnChanglen;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            description = itemView.findViewById(R.id.description);
            star = itemView.findViewById(R.id.star);
            picpath = itemView.findViewById(R.id.pic);
            btnChanglen = itemView.findViewById(R.id.btn_chanllenge);
        }
    }
}