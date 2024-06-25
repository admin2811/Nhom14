package com.englishtlu.english_learning.main.quizz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.quizz.model.CheckModel;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {

    TextView txtResult, txtTotal;
    AppCompatButton btnQuit, btnSeeReults;

    private boolean isSelect;
    private HashMap<Integer, String> selectedAnswers;
    private List<CheckModel> questions;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        txtResult = findViewById(R.id.tvScore);
        txtTotal = findViewById(R.id.tvTotal);
        btnQuit = findViewById(R.id.btnOut);
        btnSeeReults = findViewById(R.id.btnSeeResult);

        Intent intent = getIntent();
        //Lay các giá trị của điểm và tổng số câu hỏi từ intent
        int score = getIntent().getIntExtra("score", 0);
        int total = getIntent().getIntExtra("totalQues", 0);
        txtResult.setText(String.valueOf(score));
        txtTotal.setText("/" + total);

        isSelect = getIntent().getBooleanExtra("isSelect", true);

        //Lay category id
        int category_id = getIntent().getIntExtra("CATEGORY_ID", 0);
        selectedAnswers = (HashMap<Integer, String>) intent.getSerializableExtra("selectedAnswers");
        //Lay list câu hỏi
        assert selectedAnswers != null;
        for (Map.Entry<Integer, String> entry : selectedAnswers.entrySet()) {
            Log.d("ResultActivity", "Question ID: " + entry.getKey() + ", Selected Answer ID: " + entry.getValue());
        }

        fetchQuestions(category_id);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, PractieActivity.class);
                startActivity(intent);
            }
        });

        btnSeeReults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questions != null) {
                    Intent intent = new Intent(ResultActivity.this, CheckActivity.class);
                    intent.putExtra("questions", (Serializable) questions);
                    intent.putExtra("selectedAnswers", selectedAnswers);
                    startActivity(intent);
                }else{
                    Toast.makeText(ResultActivity.this, "Không có câu hỏi", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void fetchQuestions(int category_id) {
        String url = "https://nhom14-android.000webhostapp.com/test/fetch_answer.php?category_id=" + category_id;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<CheckModel>>() {
                        }.getType();
                        questions = gson.fromJson(response.toString(), listType);
                        for (CheckModel question : questions) {
                            Log.d("ResultActivity", "Question: " + question.getQuestion() + ", Correct Answer: " + question.getCorrectAnswer());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ResultActivity", "Failed to fetch questions", error);
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }
}