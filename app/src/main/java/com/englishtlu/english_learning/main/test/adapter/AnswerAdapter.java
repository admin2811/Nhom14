package com.englishtlu.english_learning.main.test.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.test.model.Answer;
import com.englishtlu.english_learning.main.test.model.Question;
import com.englishtlu.english_learning.main.test.repository.QuizRepository;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.MyViewHolder>{
    public Context context;
    private List<Answer> answerslList;
    private OnQuestionAnsweredListener onQuestionAnsweredListener;
    public interface OnQuestionAnsweredListener {
        void onQuestionAnswered(int position);
    }
    public AnswerAdapter(Context context, List<Answer> answerslList, OnQuestionAnsweredListener onQuestionAnsweredListener) {
        this.context = context;
        this.answerslList = answerslList;
        this.onQuestionAnsweredListener = onQuestionAnsweredListener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_item, parent, false);
        return new MyViewHolder(view);
    }
    @Override public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setData(position);
    }
    @Override
    public int getItemCount() {
        return answerslList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView ques, optionA, optionB, optionC, optionD;
        FrameLayout btnOptionA, btnOptionB, btnOptionC, btnOptionD;
        ImageView playmedia;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.btnOptionA = itemView.findViewById(R.id.btnOptionA);
            this.btnOptionB = itemView.findViewById(R.id.btnOptionB);
            this.btnOptionC = itemView.findViewById(R.id.btnOptionC);
            this.btnOptionD = itemView.findViewById(R.id.btnOptionD);
            this.ques = itemView.findViewById(R.id.QuestionText);
            this.optionA = itemView.findViewById(R.id.optionA);
            this.optionB = itemView.findViewById(R.id.optionB);
            this.optionC = itemView.findViewById(R.id.optionC);
            this.optionD = itemView.findViewById(R.id.optionD);
            this.playmedia = itemView.findViewById(R.id.playMedia);
        }
        private void setData(final int pos) {
            ques.setText(answerslList.get(pos).getQuestion());
            optionA.setText(answerslList.get(pos).getOptionA());
            optionB.setText(answerslList.get(pos).getOptionB());
            optionC.setText(answerslList.get(pos).getOptionC());
            optionD.setText(answerslList.get(pos).getOptionD());
            if(!answerslList.get(pos).getAnswer().equals(answerslList.get(pos).getTrueAnswer())){
                if(optionA.getText().toString().equals(answerslList.get(pos).getAnswer())){
                    rightAnswer(pos);
                    Drawable drawable = ContextCompat.getDrawable(context, R.drawable.bg_quiz_item_wrong);
                    btnOptionA.setBackground(drawable);
                    btnOptionA.setClickable(false);
                    btnOptionB.setClickable(false);
                    btnOptionC.setClickable(false);
                    btnOptionD.setClickable(false);
                } else if (optionB.getText().toString().equals(answerslList.get(pos).getAnswer())) {
                    rightAnswer(pos);
                    Drawable drawable = ContextCompat.getDrawable(context, R.drawable.bg_quiz_item_wrong);
                    btnOptionB.setBackground(drawable);
                    btnOptionA.setClickable(false);
                    btnOptionB.setClickable(false);
                    btnOptionC.setClickable(false);
                    btnOptionD.setClickable(false);
                } else if (optionC.getText().toString().equals(answerslList.get(pos).getAnswer())) {
                    rightAnswer(pos);
                    Drawable drawable = ContextCompat.getDrawable(context, R.drawable.bg_quiz_item_wrong);
                    btnOptionC.setBackground(drawable);
                    btnOptionA.setClickable(false);
                    btnOptionB.setClickable(false);
                    btnOptionC.setClickable(false);
                    btnOptionD.setClickable(false);
                } else if (optionD.getText().toString().equals(answerslList.get(pos).getAnswer())) {
                    rightAnswer(pos);
                    Drawable drawable = ContextCompat.getDrawable(context, R.drawable.bg_quiz_item_wrong);
                    btnOptionD.setBackground(drawable);
                    btnOptionA.setClickable(false);
                    btnOptionB.setClickable(false);
                    btnOptionC.setClickable(false);
                    btnOptionD.setClickable(false);
                }
            } else if (answerslList.get(pos).getAnswer() == null) {
                rightAnswer(pos);
                btnOptionA.setClickable(false);
                btnOptionB.setClickable(false);
                btnOptionC.setClickable(false);
                btnOptionD.setClickable(false);
            } else {
                rightAnswer(pos);
                btnOptionA.setClickable(false);
                btnOptionB.setClickable(false);
                btnOptionC.setClickable(false);
                btnOptionD.setClickable(false);
            }
        }
        private void rightAnswer(final int pos){
            if(optionA.getText().toString().equals(answerslList.get(pos).getTrueAnswer())){
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.bg_quiz_item_right);
                btnOptionA.setBackground(drawable);
            } else if (optionB.getText().toString().equals(answerslList.get(pos).getTrueAnswer())){
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.bg_quiz_item_right);
                btnOptionB.setBackground(drawable);
            } else if (optionC.getText().toString().equals(answerslList.get(pos).getTrueAnswer())) {
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.bg_quiz_item_right);
                btnOptionC.setBackground(drawable);
            } else if (optionD.getText().toString().equals(answerslList.get(pos).getTrueAnswer())) {
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.bg_quiz_item_right);
                btnOptionD.setBackground(drawable);
            }
        }
    }
}
