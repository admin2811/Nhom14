package com.englishtlu.english_learning.main.quizz.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.quizz.model.Answer;
import com.englishtlu.english_learning.main.quizz.model.QuestionModel;

import java.util.List;
import java.util.Map;

public class QuesstionAdapter extends RecyclerView.Adapter<QuesstionAdapter.ViewHolder> {

    private List<QuestionModel> quesList;
    private OnAnswerSelectedListener listener;
    private Map<Integer, Integer> correctAnswerMap;
    private Map<Integer, String> userSelectedQuestionsMap;

    private boolean isSelect = false;
    public interface OnAnswerSelectedListener {
        void onAnswerSelected(int questionPosition, int answerIndex);
    }
    public QuesstionAdapter(List<QuestionModel> quesList, OnAnswerSelectedListener listener, Map<Integer, Integer> correctAnswerMap, Map<Integer, String> userSelectedQuestionsMap, boolean isSelect) {
        this.quesList = quesList;
        this.listener = listener;
        this.correctAnswerMap = correctAnswerMap;
        this.userSelectedQuestionsMap = userSelectedQuestionsMap;
        this.isSelect = isSelect;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        QuestionModel question = quesList.get(position);
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
        public void bind(QuestionModel question, Integer selectedAnswerId, Integer correctAnswerId, OnAnswerSelectedListener listener) {
            // Bind data to UI components, update UI based on selectedAnswerId and correctAnswerId
            if(isSelect){
                //Nếu người dung chọn đúng thì tô màu xanh còn không thì sẽ tô màu đỏ cho câu trả lời của người dung và hiện thị xanh cho câu trả lời đúng
                if(selectedAnswerId != null){
                    if(selectedAnswerId == correctAnswerId){
                        switch (selectedAnswerId){
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
                    }else{
                        switch (selectedAnswerId){
                            case 0:
                                optionA.setBackgroundResource(R.drawable.wrong_btn);
                                break;
                            case 1:
                                optionB.setBackgroundResource(R.drawable.wrong_btn);
                                break;
                            case 2:
                                optionC.setBackgroundResource(R.drawable.wrong_btn);
                                break;
                            case 3:
                                optionD.setBackgroundResource(R.drawable.wrong_btn);
                                break;
                        }
                        switch (correctAnswerId){
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
                }else{
                    Toast.makeText(itemView.getContext(), "Chưa chọn câu trả lời", Toast.LENGTH_SHORT).show();
                }
            }
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
