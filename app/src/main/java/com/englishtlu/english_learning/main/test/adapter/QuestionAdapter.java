package com.englishtlu.english_learning.main.test.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.test.model.Answer;
import com.englishtlu.english_learning.main.test.model.Question;
import com.englishtlu.english_learning.main.test.repository.QuizRepository;

import java.util.List;
import java.util.logging.Handler;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {
    public int points;
    public int wrong;
    private Handler handler;
    public Context context;
    private QuizRepository quizRepository = new QuizRepository();
    public boolean checkChoice = false;
    private List<Question> questionModelList;
    private OnQuestionAnsweredListener onQuestionAnsweredListener;
    public interface OnQuestionAnsweredListener {
        void onQuestionAnswered(int position);
    }
    public List<Question> getQuestionModelList(){
        return this.questionModelList;
    }
    public QuestionAdapter(Context context, List<Question> questionModelList, OnQuestionAnsweredListener onQuestionAnsweredListener) {
        this.context = context;
        this.questionModelList = questionModelList;
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

    @Override public int getItemCount() {
        return questionModelList.size();
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
        private void setData(final int pos){
            ques.setText(questionModelList.get(pos).getQuestion());
            optionA.setText(questionModelList.get(pos).getOptionA());
            optionB.setText(questionModelList.get(pos).getOptionB());
            optionC.setText(questionModelList.get(pos).getOptionC());
            optionD.setText(questionModelList.get(pos).getOptionD());
            if(questionModelList.get(pos).getQuestionType() == "listen"){
                String audioFilePath = questionModelList.get(pos).getUrlMedia();
                MediaPlayer mediaPlayer = MediaPlayer.create(context, Uri.parse(audioFilePath));
                if (mediaPlayer != null) {
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mediaPlayer.start();
                        }
                    });

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mediaPlayer.release();
                        }
                    });

                    mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            // Xử lý lỗi ở đây
                            return true;
                        }
                    });
                } else {
                    Toast.makeText(context, "No media file found.", Toast.LENGTH_SHORT).show();
                }
            } else{
                playmedia.setVisibility(View.VISIBLE);
            }


            btnOptionA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!optionA.getText().toString().equals(questionModelList.get(pos).getAnswer())){
                        rightAnswer(pos);
                        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.bg_quiz_item_wrong);
                        btnOptionA.setBackground(drawable);
                        btnOptionA.setClickable(false);
                        btnOptionB.setClickable(false);
                        btnOptionC.setClickable(false);
                        btnOptionD.setClickable(false);
                        QuizRepository.nuTrue++;
                        QuizRepository.answersData.add(new Answer(questionModelList.get(pos).getQuestion(),questionModelList.get(pos).getQuestionType(),optionA.getText().toString(),questionModelList.get(pos).getAnswer(),questionModelList.get(pos).getIdQuiz(),questionModelList.get(pos).getOptionA(),questionModelList.get(pos).getOptionB(),questionModelList.get(pos).getOptionC(),questionModelList.get(pos).getOptionD(),questionModelList.get(pos).getUrlMedia()));
                    }
                    else{
                        rightAnswer(pos);
                        btnOptionA.setClickable(false);
                        btnOptionB.setClickable(false);
                        btnOptionC.setClickable(false);
                        btnOptionD.setClickable(false);
                        QuizRepository.nuWrong++;
                        QuizRepository.answersData.add(new Answer(questionModelList.get(pos).getQuestion(),questionModelList.get(pos).getQuestionType(),optionA.getText().toString(),questionModelList.get(pos).getAnswer(),questionModelList.get(pos).getIdQuiz(),questionModelList.get(pos).getOptionA(),questionModelList.get(pos).getOptionB(),questionModelList.get(pos).getOptionC(),questionModelList.get(pos).getOptionD(),questionModelList.get(pos).getUrlMedia()));
                    }
                    checkChoice = true;
                    onQuestionAnsweredListener.onQuestionAnswered(pos);
                }
            });

            btnOptionB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!optionB.getText().toString().equals(questionModelList.get(pos).getAnswer())){
                        rightAnswer(pos);
                        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.bg_quiz_item_wrong);
                        btnOptionB.setBackground(drawable);
                        btnOptionA.setClickable(false);
                        btnOptionB.setClickable(false);
                        btnOptionC.setClickable(false);
                        btnOptionD.setClickable(false);
                        QuizRepository.answersData.add(new Answer(questionModelList.get(pos).getQuestion(),questionModelList.get(pos).getQuestionType(),optionB.getText().toString(),questionModelList.get(pos).getAnswer(),questionModelList.get(pos).getIdQuiz(),questionModelList.get(pos).getOptionA(),questionModelList.get(pos).getOptionB(),questionModelList.get(pos).getOptionC(),questionModelList.get(pos).getOptionD(),questionModelList.get(pos).getUrlMedia()));
                        QuizRepository.nuTrue++;
                    }else {
                        rightAnswer(pos);
                        btnOptionA.setClickable(false);
                        btnOptionB.setClickable(false);
                        btnOptionC.setClickable(false);
                        btnOptionD.setClickable(false);
                        QuizRepository.nuWrong++;
                        QuizRepository.answersData.add(new Answer(questionModelList.get(pos).getQuestion(),questionModelList.get(pos).getQuestionType(),optionB.getText().toString(),questionModelList.get(pos).getAnswer(),questionModelList.get(pos).getIdQuiz(),questionModelList.get(pos).getOptionA(),questionModelList.get(pos).getOptionB(),questionModelList.get(pos).getOptionC(),questionModelList.get(pos).getOptionD(),questionModelList.get(pos).getUrlMedia()));
                    }
                    checkChoice = true;
                    onQuestionAnsweredListener.onQuestionAnswered(pos);
                }

            });

            btnOptionC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!optionC.getText().toString().equals(questionModelList.get(pos).getAnswer())){
                        rightAnswer(pos);
                        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.bg_quiz_item_wrong);
                        btnOptionC.setBackground(drawable);
                        btnOptionA.setClickable(false);
                        btnOptionB.setClickable(false);
                        btnOptionC.setClickable(false);
                        btnOptionD.setClickable(false);
                        QuizRepository.nuTrue++;
                        QuizRepository.answersData.add(new Answer(questionModelList.get(pos).getQuestion(),questionModelList.get(pos).getQuestionType(),optionC.getText().toString(),questionModelList.get(pos).getAnswer(),questionModelList.get(pos).getIdQuiz(),questionModelList.get(pos).getOptionA(),questionModelList.get(pos).getOptionB(),questionModelList.get(pos).getOptionC(),questionModelList.get(pos).getOptionD(),questionModelList.get(pos).getUrlMedia()));
                    }
                    else {
                        rightAnswer(pos);
                        btnOptionA.setClickable(false);
                        btnOptionB.setClickable(false);
                        btnOptionC.setClickable(false);
                        btnOptionD.setClickable(false);
                        QuizRepository.nuWrong++;
                        QuizRepository.answersData.add(new Answer(questionModelList.get(pos).getQuestion(),questionModelList.get(pos).getQuestionType(),optionC.getText().toString(),questionModelList.get(pos).getAnswer(),questionModelList.get(pos).getIdQuiz(),questionModelList.get(pos).getOptionA(),questionModelList.get(pos).getOptionB(),questionModelList.get(pos).getOptionC(),questionModelList.get(pos).getOptionD(),questionModelList.get(pos).getUrlMedia()));
                    }
                    checkChoice = true;
                    onQuestionAnsweredListener.onQuestionAnswered(pos);
                }
            });

            btnOptionD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!optionD.getText().toString().equals(questionModelList.get(pos).getAnswer())){
                        rightAnswer(pos);
                        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.bg_quiz_item_wrong);
                        btnOptionD.setBackground(drawable);
                        btnOptionA.setClickable(false);
                        btnOptionB.setClickable(false);
                        btnOptionC.setClickable(false);
                        btnOptionD.setClickable(false);
                        QuizRepository.nuTrue++;
                        QuizRepository.answersData.add(new Answer(questionModelList.get(pos).getQuestion(),questionModelList.get(pos).getQuestionType(),optionD.getText().toString(),questionModelList.get(pos).getAnswer(),questionModelList.get(pos).getIdQuiz(),questionModelList.get(pos).getOptionA(),questionModelList.get(pos).getOptionB(),questionModelList.get(pos).getOptionC(),questionModelList.get(pos).getOptionD(),questionModelList.get(pos).getUrlMedia()));
                    }else {
                        rightAnswer(pos);
                        btnOptionA.setClickable(false);
                        btnOptionB.setClickable(false);
                        btnOptionC.setClickable(false);
                        btnOptionD.setClickable(false);
                        QuizRepository.nuWrong++;
                        QuizRepository.answersData.add(new Answer(questionModelList.get(pos).getQuestion(),questionModelList.get(pos).getQuestionType(),optionD.getText().toString(),questionModelList.get(pos).getAnswer(),questionModelList.get(pos).getIdQuiz(),questionModelList.get(pos).getOptionA(),questionModelList.get(pos).getOptionB(),questionModelList.get(pos).getOptionC(),questionModelList.get(pos).getOptionD(),questionModelList.get(pos).getUrlMedia()));
                    }
                    checkChoice = true;
                    onQuestionAnsweredListener.onQuestionAnswered(pos);
                }
            });
        }
        private void rightAnswer(final int pos){
            if(optionA.getText().toString().equals(questionModelList.get(pos).getAnswer())){
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.bg_quiz_item_right);
                btnOptionA.setBackground(drawable);
            } else if (optionB.getText().toString().equals(questionModelList.get(pos).getAnswer())){
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.bg_quiz_item_right);
                btnOptionB.setBackground(drawable);
            } else if (optionC.getText().toString().equals(questionModelList.get(pos).getAnswer())) {
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.bg_quiz_item_right);
                btnOptionC.setBackground(drawable);
            } else if (optionD.getText().toString().equals(questionModelList.get(pos).getAnswer())) {
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.bg_quiz_item_right);
                btnOptionD.setBackground(drawable);
            }
        }
    }
}
