package com.englishtlu.english_learning.main.test.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.test.model.Question;
import com.englishtlu.english_learning.main.test.model.Quiz;
import com.englishtlu.english_learning.main.test.repository.QuizRepository;

import java.util.ArrayList;
import java.util.List;

public class QuizListAdapter extends RecyclerView.Adapter<QuizListAdapter.MyViewHolder>{
    private List<Quiz> quizList;
    private ArrayList<Question> questionList;
    private QuizItemClickListener clickListener;
    public Context context;
    private QuizRepository quizRepository;
    public interface QuizItemClickListener {
        void onQuizItemClick(ArrayList<Question> questionList,int id);
    }
    public QuizListAdapter(Context context, List<Quiz> quizList, QuizItemClickListener clickListener){
        this.context = context;
        this.quizList = quizList;
        this.clickListener = clickListener;
        this.quizRepository = new QuizRepository(context);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_list, parent, false);
        return new MyViewHolder(view);
    }
    @Override public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setData(position);
    }
    @Override public int getItemCount() {
        return quizList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView quizName;
        public ConstraintLayout quizItem;
        MyViewHolder(@NonNull View itemView){
            super(itemView);
            this.quizName = itemView.findViewById(R.id.quizName);
            this.quizItem = itemView.findViewById(R.id.quizItem);
        }
        private void setData(final int pos){
            quizName.setText(quizList.get(pos).getNameQuiz());
            quizItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context,Integer.toString(quizList.get(pos).getIdQuiz()),Toast.LENGTH_SHORT).show();
                    questionList = quizRepository.readQuiz(Integer.toString(quizList.get(pos).getIdQuiz()));
                    //Toast.makeText(context,questionList.get(pos).getQuestion().toString(),Toast.LENGTH_SHORT).show();
                    clickListener.onQuizItemClick(questionList,quizList.get(pos).getIdQuiz());
                }
            });
        }
    }
}
