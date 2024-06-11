package com.englishtlu.english_learning.main.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    static ArrayList<Course> items;

    @SuppressLint("StaticFieldLeak")
    static Context context;

    private static OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(Course course);
    }
    public  CourseAdapter(ArrayList<Course> items, OnItemClickListener listener) {
        CourseAdapter.items = items;
        mListener = listener;
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
        holder.bind(item);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(item);
            }
        });

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
            // Bắt sự kiện khi item được click
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Course course = items.get(position);
                        mListener.onItemClick(course);
                    }
                }
            });
        }

        // Bind dữ liệu vào ViewHolder
        public void bind(Course course) {
            title.setText(course.getTitle());
            description.setText(course.getDescription());
            star.setText(new DecimalFormat("##.#").format(course.getStar()));
            @SuppressLint("DiscouragedApi") int drawableResourceId = context.getResources().getIdentifier(course.getPicpath(), "drawable", context.getPackageName());
            Glide.with(itemView.getContext())
                    .load(drawableResourceId)
                    .into(picpath);
        }

    }
}
