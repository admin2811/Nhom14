package com.englishtlu.english_learning.main.quizz.adapter;

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
import com.englishtlu.english_learning.main.quizz.model.LessonVocab;

import java.util.ArrayList;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.ViewHolder> {

    static ArrayList<LessonVocab> items;

    static Context context;
    private static OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(LessonVocab lessonVocab);
    }


    public LessonAdapter(ArrayList<LessonVocab> items, OnItemClickListener listener) {
        LessonAdapter.items = items;
        mListener =  listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quizz,parent,false);
        context = parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LessonVocab item = items.get(position);
        holder.bind(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView picpath;
        TextView title, description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            picpath = itemView.findViewById(R.id.ivLessonImage);
            title = itemView.findViewById(R.id.tvLessonName);
            description = itemView.findViewById(R.id.tvLessonDec);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        LessonVocab lessonVocab = items.get(position);
                        mListener.onItemClick(lessonVocab);
                    }

                }
            });

        }
        public void bind(LessonVocab lesson){
            title.setText(lesson.getTitle());
            description.setText(lesson.getDescription());
            @SuppressLint("DiscouragedApi") int drawableResourceId = context.getResources().getIdentifier(lesson.getPicpath(), "drawable", context.getPackageName());
            Glide.with(itemView.getContext())
                    .load(drawableResourceId)
                    .into(picpath);
        }
    }
}
