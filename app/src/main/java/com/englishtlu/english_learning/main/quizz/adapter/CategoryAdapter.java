package com.englishtlu.english_learning.main.quizz.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.home.model.Course;
import com.englishtlu.english_learning.main.quizz.model.Category;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context context;
    private List<Category> categoryList;
    private static OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(Category category);
    }

    public CategoryAdapter(Context context, List<Category> categoryList, OnItemClickListener listener) {
        this.context = context;
        this.categoryList = categoryList;
        mListener = listener;
    }
    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_practice, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.bind(category);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(category);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDescription;
        ImageView ivImage;
        public CategoryViewHolder(@NonNull android.view.View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvLessonName);
            tvDescription = itemView.findViewById(R.id.tvLessonDec);
            ivImage = itemView.findViewById(R.id.ivLessonImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Category category = categoryList.get(position);
                        mListener.onItemClick(category);
                    }

                }
            });
        }
        public void bind(Category category) {
            tvName.setText(category.getName());
            tvDescription.setText(category.getDescription());
            //sử dụng Gilde để load ảnh từ url
            //Định dạng url ảnh là https://nhom14-android.000webhostapp.com/test/uploads/ + tên ảnh
            Picasso.get().load("https://nhom14-android.000webhostapp.com/test/uploads/" + category.getImage()).into(ivImage, new Callback() {
                @Override
                public void onSuccess() {
                    Log.d("CategoryAdapter", "Image loaded successfully");
                }

                @Override
                public void onError(Exception e) {
                    Log.e("CategoryAdapter", "Error loading image: " + e.getMessage());
                }
            });
        }
    }
}
