package com.englishtlu.english_learning.main.quizz;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.englishtlu.english_learning.main.quizz.adapter.QuesstionAdapter;
import com.englishtlu.english_learning.main.quizz.model.QuestionModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.englishtlu.english_learning.databinding.ActivityQuesstionBinding;

import com.englishtlu.english_learning.R;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

public class QuesstionActivity extends AppCompatActivity {

    private RecyclerView questionView;
    private TextView tvQuestId,btnSub,tvTotalQues;
    private AppCompatButton btnNEXT,btnPREV;
    private QuesstionAdapter adapter;
    private List<QuestionModel> questionList;

    private  int question_id;
    private  int totalQues;
    private int selectedAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quesstion);


        int categoryID = getIntent().getIntExtra("CATEGORY_ID", -1);
        if(categoryID == -1){
            // Handle the case where CATEGORY_ID is not passed
            Snackbar.make(questionView, "Invalid category ID", Snackbar.LENGTH_SHORT).show();
            finish();
            return;
        }else {
            fetchQuestions(categoryID);
        }
        init();
        setSnapHelper();
        setOnclik();

    }

    private void setOnclik() {
        btnNEXT.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(question_id < totalQues - 1){
                    question_id++;
                    questionView.smoothScrollToPosition(question_id);
                    tvQuestId.setText("Question: " + (question_id + 1));
                }
            }
        });

        btnPREV.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(question_id > 0){
                    question_id--;
                    questionView.smoothScrollToPosition(question_id);
                    tvQuestId.setText("Question: " + (question_id + 1));
                }
            }
        });

    }

    private void setSnapHelper() {

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(questionView);

        questionView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                View view = snapHelper.findSnapView(questionView.getLayoutManager());
                if(view != null){
                    question_id = Objects.requireNonNull(questionView.getLayoutManager()).getPosition(view);
                    tvQuestId.setText("Question: " + (question_id + 1));
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void fetchQuestions(int categoryID) {
        String url = "https://nhom14-android.000webhostapp.com/test/fetch_questions.php?category_id=" + categoryID;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<QuestionModel>>() {}.getType();
                        List<QuestionModel> questionList = gson.fromJson(response.toString(), listType);

                        QuesstionAdapter adapter = new QuesstionAdapter(questionList);
                        questionView.setAdapter(adapter);

                        totalQues = questionList.size();
                        tvTotalQues.setText("/" + totalQues);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("QuesstionActivity", "Error fetching questions", error);
                        Toast.makeText(QuesstionActivity.this, "Error fetching questions", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(jsonArrayRequest);
    }

    @SuppressLint({"WrongViewCast", "SetTextI18n"})
    private void init(){
        tvQuestId = findViewById(R.id.tv_quesID);
        tvTotalQues = findViewById(R.id.tv_quesTotal);
        questionView = findViewById(R.id.question_view);
        btnNEXT = findViewById(R.id.btnNext);
        btnPREV = findViewById(R.id.btnGoBack);
        questionView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        questionView.setHasFixedSize(true);
        question_id = 0;
        tvQuestId.setText("Question: " + (question_id + 1));
        tvTotalQues.setText("0");
    }
}