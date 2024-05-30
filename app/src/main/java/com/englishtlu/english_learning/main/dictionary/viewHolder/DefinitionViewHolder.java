package com.englishtlu.english_learning.main.dictionary.viewHolder;



import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.englishtlu.english_learning.R;


public class DefinitionViewHolder extends RecyclerView.ViewHolder {
    public TextView textView_definition;
    public TextView textView_example;
    public TextView textView_synonyns;
    public TextView textView_antonyns;

    @SuppressLint("CutPasteId")
    public DefinitionViewHolder(@NonNull View itemView) {
        super(itemView);
        textView_definition = itemView.findViewById(R.id.textView_definition);
        textView_example = itemView.findViewById(R.id.textView_example);
        textView_synonyns = itemView.findViewById(R.id.textView_synonyns);
        textView_antonyns = itemView.findViewById(R.id.textView_antonyns);
    }
}