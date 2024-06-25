package com.englishtlu.english_learning.main.Grammar;

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

import java.util.ArrayList;

public class GrammarAdapter extends RecyclerView.Adapter<GrammarAdapter.ViewHolder>{
    Context context;
    ArrayList<Grammar> arrayList;
    OnItemClickListener onItemClickListener;

    public GrammarAdapter(Context context, ArrayList<Grammar> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public GrammarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_grammar_item,parent,false);
        return new GrammarAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GrammarAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(arrayList.get(position).getUrl()).into(holder.imageView);
        holder.title.setText(arrayList.get(position).getTitle());
        holder.itemView.setOnClickListener(view -> onItemClickListener.onClick(arrayList.get(position)));
    }

    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.grammar_item_image);
            title = itemView.findViewById(R.id.grammar_item_title);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(Grammar grammar);
    }
}
