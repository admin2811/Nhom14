package com.englishtlu.english_learning.main.quizz.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.quizz.model.DisplayedQuestionModel;

import java.util.List;

public class CheckAdapter extends RecyclerView.Adapter<CheckAdapter.CheckViewHolder>{
    private List<DisplayedQuestionModel> displayedQuestions;

    public CheckAdapter(List<DisplayedQuestionModel> displayedQuestions) {
        this.displayedQuestions = displayedQuestions;
    }
    @NonNull
    @Override
    public CheckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result, parent, false);
        return new CheckViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckViewHolder holder, int position) {
        DisplayedQuestionModel question = displayedQuestions.get(position);
        holder.tvQuestion.setText(question.getQuestion());
        holder.tvCorrectAnswer.setText(question.getCorrectAnswer());
        holder.tvYourAnswer.setText(question.getSelectedAnswer());
    }

    @Override
    public int getItemCount() {
        return displayedQuestions.size();
    }

    public class CheckViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion,tvCorrectAnswer,tvYourAnswer;
        public CheckViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            tvCorrectAnswer = itemView.findViewById(R.id.tvCorrectAnswer);
            tvYourAnswer = itemView.findViewById(R.id.tvYourAnswer);
        }
    }
}
