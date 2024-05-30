package com.englishtlu.english_learning.main.profile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.profile.model.FAQ;

import java.util.ArrayList;
import java.util.List;

public class FAQadapter extends RecyclerView.Adapter<FAQadapter.ViewHolder> {

    Context context;
     ArrayList<FAQ> FAQList;

    public FAQadapter(Context context,ArrayList<FAQ> FAQList) {
        this.context = context;
        this.FAQList = FAQList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_faq,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FAQ faq = FAQList.get(position);
        holder.questionTxt.setText(faq.getQuestion());
        holder.answerTxt.setText(faq.getAnswer());

        boolean isExpandable = FAQList.get(position).isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return FAQList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView questionTxt, answerTxt;
        LinearLayout linearLayout;
        RelativeLayout expandableLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTxt = itemView.findViewById(R.id.question);
            answerTxt = itemView.findViewById(R.id.answer);

            linearLayout = itemView.findViewById(R.id.linear_layout);
            expandableLayout = itemView.findViewById(R.id.expanded_layout);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FAQ faq = FAQList.get(getAdapterPosition());
                    faq.setExpandable(!faq.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
