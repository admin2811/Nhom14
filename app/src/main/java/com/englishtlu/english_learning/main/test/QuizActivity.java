package com.englishtlu.english_learning.main.test;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.test.adapter.QuestionAdapter;
import com.englishtlu.english_learning.main.test.database.QuizDatabase;
import com.englishtlu.english_learning.main.test.model.Question;
import com.englishtlu.english_learning.main.test.model.Quiz;
import com.englishtlu.english_learning.main.test.repository.QuizRepository;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class QuizActivity extends AppCompatActivity {
    private Context context;
    SQLiteDatabase myDB;
    QuizDatabase dbHelper;
    private QuizRepository quizRepository;
    private static ArrayList<Question> questionsData  = new ArrayList<>();
    private RecyclerView questionView;
    private TextView txtTimer;
    private AppCompatButton btnSkip;
    private ProgressBar quesProgress;
    private int quesID;
    private int quizID;
    private int noQues = 1, progress = 1;
    QuestionAdapter questionAdapter;
    private CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new QuizDatabase(this);

        context = this;
        questionView = findViewById(R.id.question_recycleView);
        quesProgress = findViewById(R.id.progressBarQuestion);
        txtTimer = findViewById(R.id.timeDuration);
        btnSkip = findViewById(R.id.btnSkip);

        quizID = getIntent().getIntExtra("idQuiz",0);

        quizRepository = new QuizRepository(this);

        questionsData = quizRepository.readQuiz(Integer.toString(quizID));

        questionAdapter = new QuestionAdapter(this,questionsData, new QuestionAdapter.OnQuestionAnsweredListener(){
            @Override
            public void onQuestionAnswered(int position) {
                if (questionAdapter.checkChoice) {
                    if (quesID < questionsData.size()) {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                questionView.smoothScrollToPosition(quesID + 1);
                                updateProgressBar();
                            }
                        },1000);
                    }
                    questionAdapter.checkChoice = false;
                }
                if (quesID == (questionsData.size() - 1)) {
                    Intent intent = new Intent(QuizActivity.this, QuizResultActivity.class);
                    intent.putExtra("idQuiz",quizID);
                    startActivity(intent);
                }
            }
        });
        questionView.setAdapter(questionAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        questionView.setLayoutManager(layoutManager);

        startTimer();

        setSnapHelper();

        setclickSkip();

        progress = noQues * 100 / questionsData.size();
        quesProgress.setProgress(progress);

    }
    private void setSnapHelper(){
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(questionView);

        questionView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                View view = snapHelper.findSnapView(recyclerView.getLayoutManager());
                quesID = recyclerView.getLayoutManager().getPosition(view);

                if (timer != null) {
                    timer.cancel();
                }

                startTimer();

                if(noQues == questionsData.size()){
                    int hexColor = 0xFF93faa5;
                    btnSkip.setText("Finish");
                    btnSkip.setBackgroundTintList(ColorStateList.valueOf(hexColor));
                }
            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        questionView.setOnTouchListener((v, event) -> true);
    }
    private void setclickSkip(){
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quesID < questionsData.size() - 1){
                    Toast.makeText(QuizActivity.this,Integer.toString(quesID) + " AND " + Integer.toString(questionsData.size()),Toast.LENGTH_SHORT).show();
                    questionView.smoothScrollToPosition(quesID + 1);
                    updateProgressBar();
                    QuizRepository.nuNoChoice++;
                } else if (quesID == (questionsData.size() - 1)) {
                    Intent intent = new Intent(QuizActivity.this, QuizResultActivity.class);
                    intent.putExtra("idQuiz",quizID);
                    startActivity(intent);
                    QuizRepository.nuNoChoice++;
                }
            }
        });
    }

    private void updateProgressBar() {
        noQues++;
        progress = noQues * 100 / questionsData.size();
        quesProgress.setProgress(progress);
    }
    private void startTimer(){
        timer = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                String time = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                txtTimer.setText(time);
            }

            @Override
            public void onFinish() {
                Toast.makeText(QuizActivity.this,Integer.toString(quesID) + " AND " + Integer.toString(questionsData.size()),Toast.LENGTH_SHORT).show();
                questionView.smoothScrollToPosition(quesID + 1);
                QuizRepository.nuNoChoice++;
                updateProgressBar();
                if (quesID == (questionsData.size() - 1)) {
                    Intent intent = new Intent(QuizActivity.this, QuizResultActivity.class);
                    intent.putExtra("idQuiz",quizID);
                    startActivity(intent);
                }
            }
        };
        timer.start();
    }
}