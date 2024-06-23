package com.englishtlu.english_learning.main.quizz.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.quizz.model.Answer;
import com.englishtlu.english_learning.main.quizz.model.QuestionModel;

import java.util.List;

public class QuesstionAdapter extends RecyclerView.Adapter<QuesstionAdapter.ViewHolder> {

    private List<QuestionModel> quesList;

    public QuesstionAdapter(List<QuestionModel> quesList) {
        this.quesList = quesList;
    }

    @NonNull
    @Override
    public QuesstionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuesstionAdapter.ViewHolder holder, int position) {
        holder.setData(quesList.get(position));
    }

    @Override
    public int getItemCount() {
        return quesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView ques;
        private Button optionA, optionB, optionC, optionD;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ques = itemView.findViewById(R.id.tv_question);
            optionA = itemView.findViewById(R.id.optionA);
            optionB = itemView.findViewById(R.id.optionB);
            optionC = itemView.findViewById(R.id.optionC);
            optionD = itemView.findViewById(R.id.optionD);

        }
        private void setData(QuestionModel questionModel){
            ques.setText(quesList.get(getAdapterPosition()).getQuestion());
            List<Answer> answers = questionModel.getAnswers();
            if(answers.size() >= 4){
                optionA.setText(answers.get(0).getAnswer());
                optionB.setText(answers.get(1).getAnswer());
                optionC.setText(answers.get(2).getAnswer());
                optionD.setText(answers.get(3).getAnswer());
            }
        }
    }

}
