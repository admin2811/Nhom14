package com.englishtlu.english_learning.main.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.home.model.Course;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>{
    ArrayList<Course> items;

    Context context;
    public  CourseAdapter(ArrayList<Course> items) {
        this.items = items;
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
        Course item = items.get(position);
        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());
        holder.star.setText(new DecimalFormat("##.#").format(item.getStar()));
        @SuppressLint("DiscouragedApi") int drawableResourceId = context.getResources().getIdentifier(item.getPicpath(), "drawable", context.getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.picpath);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView title, description, star;

        ImageView picpath;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            description = itemView.findViewById(R.id.description);
            star = itemView.findViewById(R.id.star);
            picpath = itemView.findViewById(R.id.pic);
        }
    }
}
