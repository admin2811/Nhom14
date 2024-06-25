package com.englishtlu.english_learning.main.quizz.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.quizz.model.Answer;
import com.englishtlu.english_learning.main.quizz.model.QuestionModel;

import org.json.JSONArray;

import java.util.List;

public class QuesstionAdapter extends RecyclerView.Adapter<QuesstionAdapter.ViewHolder> {

    private List<QuestionModel> quesList;
    private OnAnswerSelectedListener listener;

    public interface OnAnswerSelectedListener {
        void onAnswerSelected(int questionPosition, int answerIndex);
    }
    public QuesstionAdapter(List<QuestionModel> quesList, OnAnswerSelectedListener listener) {
        this.quesList = quesList;
        this.listener = listener;

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

            optionA.setOnClickListener(v -> onOptionClicked(0));
            optionB.setOnClickListener(v -> onOptionClicked(1));
            optionC.setOnClickListener(v -> onOptionClicked(2));
            optionD.setOnClickListener(v -> onOptionClicked(3));
            

        }
        @SuppressLint("NotifyDataSetChanged")
        private void onOptionClicked(int optionIndex) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                QuestionModel questionModel = quesList.get(position);
                questionModel.setSelectedAnswer(optionIndex);
                notifyDataSetChanged(); // Refresh RecyclerView to update UI
                listener.onAnswerSelected(position, optionIndex);
            }
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

            int selectedAnswer = questionModel.getSelectedAnswer();
            highlightSelectedAnswer(selectedAnswer);
        }

        private void highlightSelectedAnswer(int selectedAnswer) {
            optionA.setBackgroundResource(R.drawable.unselected_btn);
            optionB.setBackgroundResource(R.drawable.unselected_btn);
            optionC.setBackgroundResource(R.drawable.unselected_btn);
            optionD.setBackgroundResource(R.drawable.unselected_btn);

            switch (selectedAnswer){
                case 0:
                    optionA.setBackgroundResource(R.drawable.selected_btn);
                    break;
                case 1:
                    optionB.setBackgroundResource(R.drawable.selected_btn);
                    break;
                case 2:
                    optionC.setBackgroundResource(R.drawable.selected_btn);
                    break;
                case 3:
                    optionD.setBackgroundResource(R.drawable.selected_btn);
                    break;
            }
        }
    }

}
