package com.englishtlu.english_learning.main.quizz;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.quizz.adapter.QuesstionAdapter;
import com.englishtlu.english_learning.main.quizz.model.QuestionCorrectAwnser;
import com.englishtlu.english_learning.main.quizz.model.QuestionModel;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class QuesstionActivity extends AppCompatActivity implements QuesstionAdapter.OnAnswerSelectedListener {

    private RecyclerView questionView;
    private TextView tvQuestId, btnSub, tvTotalQues;
    private AppCompatButton btnNEXT, btnPREV;
    private QuesstionAdapter adapter;
    private List<QuestionModel> questionList;
    private List<QuestionCorrectAwnser> correctAwnserList = new ArrayList<>();
    private ImageView btnBack;
    private int question_id;
    private int totalQues;
    private int score = 0;

    private boolean isSelect = false;
    private List<QuestionModel> selectedQuestions = new ArrayList<>();
    //private int correctAnswer;
    private Map<Integer,Integer> correctAnswerMap = new HashMap<>();
    private Map<Integer, String> userSelectedQuestionsMap = new HashMap<>();
    @SuppressLint({"MissingInflatedId", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quesstion);
        btnBack = findViewById(R.id.ivback);
        btnSub = findViewById(R.id.btnSubmit);
        isSelect = getIntent().getBooleanExtra("isSelect",false);
        int categoryID = getIntent().getIntExtra("CATEGORY_ID", -1);
        if (categoryID == -1) {
            // Handle the case where CATEGORY_ID is not passed
           Toast.makeText(this, "Category ID is not passed", Toast.LENGTH_SHORT).show();
            finish();
            return;
        } else {
            fetchQuestions(categoryID);
            fetchCorrectAnswer(categoryID);
        }
        init();
        setSnapHelper();
        setOnclik();
        //Lay isSelect từ intent

    }

    private void setOnclik() {

        btnNEXT.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (question_id < totalQues - 1) {
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
                if (question_id > 0) {
                    question_id--;
                    questionView.smoothScrollToPosition(question_id);
                    tvQuestId.setText("Question: " + (question_id + 1));
                }
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị thông báo bạn có chắc muốn nộp bài
                AlertDialog.Builder builder = new AlertDialog.Builder(QuesstionActivity.this);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có chắc muốn nộp bài không?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Chuyển sang màn hình kết quả
                        Intent intent = new Intent(QuesstionActivity.this, ResultActivity.class);
                        intent.putExtra("score", score);
                        intent.putExtra("totalQues", totalQues);
                        intent.putExtra("isSelect", isSelect=true);
                        // Truyền userSelectedQuestionsMap
                        intent.putExtra("selectedAnswers", new HashMap<>(userSelectedQuestionsMap));
                        //Truyền correctAnswerMap
                        intent.putExtra("correctAnswers", new HashMap<>(correctAnswerMap));
                        //Truyền categoryId hiện tại sang màn hình kết quả
                        intent.putExtra("CATEGORY_ID", getIntent().getIntExtra("CATEGORY_ID", -1));
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Đóng dialog nếu người dùng chọn hủy
                        dialog.dismiss();
                    }
                });

                // Hiển thị dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QuesstionActivity.this);
                builder.setTitle("Xác nhận");
                builder.setMessage("Bạn có chắc muốn thoát không?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish(); // Đóng activity khi người dùng xác nhận muốn thoát
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Đóng dialog nếu người dùng chọn hủy
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
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
                if (view != null) {
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
        private void fetchCorrectAnswer(int categoryID) {
            String url = "https://nhom14-android.000webhostapp.com/test/fetch_correct_answer.php?category_id=" + categoryID;
            RequestQueue queue = Volley.newRequestQueue(this);
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                if(response.length()>0){
                                    correctAwnserList = new ArrayList<>();
                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject jsonObject = response.getJSONObject(i);
                                        int id = jsonObject.getInt("id");
                                        String question = jsonObject.getString("question");
                                        int correctAnswer = jsonObject.getInt("correct_answer");
                                        String categoryName = jsonObject.getString("category_name");

                                        QuestionCorrectAwnser q = new QuestionCorrectAwnser(id, question, correctAnswer, categoryName);
                                        correctAwnserList.add(q);
                                        correctAnswerMap.put(id,correctAnswer);
                                    }

                                    //HiỂN THỊ correctAnswerList
                                    for(QuestionCorrectAwnser correctAwnser : correctAwnserList){
                                        Log.d("QuestionActivity", "Question ID: " + correctAwnser.getQuestion() + ", Correct answer: " + correctAwnser.getCorrectAnswer());
                                    }
                                }

                            } catch (JsonSyntaxException e) {
                                Log.e("QuestionActivity", "JSON parsing error", e);
                                Toast.makeText(QuesstionActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("QuestionActivity", "Error fetching correct answer", error);
                            Toast.makeText(QuesstionActivity.this, "Error fetching correct answer", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
            queue.add(jsonArrayRequest);
        }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
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
                        Type listType = new TypeToken<List<QuestionModel>>() {
                        }.getType();
                        questionList = gson.fromJson(response.toString(), listType);
                        for (QuestionModel question : questionList) {
                            question.setSelectedAnswer(-1);
                        }
                        adapter = new QuesstionAdapter(questionList, QuesstionActivity.this, correctAnswerMap, userSelectedQuestionsMap,isSelect);
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
        private void init() {
            tvQuestId = findViewById(R.id.tv_quesID);
            tvTotalQues = findViewById(R.id.tv_quesTotal);
            questionView = findViewById(R.id.question_view);
            btnNEXT = findViewById(R.id.btnNext);
            btnPREV = findViewById(R.id.btnGoBack);
            questionView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            questionView.setHasFixedSize(true);
            question_id = 0;
            tvQuestId.setText("Question: " + (question_id + 1));
            tvTotalQues.setText("0");
        }

        @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
        @Override
        public void onAnswerSelected(int questionPosition, int answerIndex) {
            QuestionModel question = questionList.get(questionPosition);
            int previousSelectedAnswer = question.getSelectedAnswer();
            //Tên nội dung câu hỏi đã chọn
            String selectedAnswerContent = question.getAnswers().get(answerIndex).getAnswer(); // Lấy nội dung câu trả lời đã chọn
            question.setSelectedAnswer(answerIndex);
            int selectedAnswerId = question.getAnswers().get(answerIndex).getAnswer_id();
            int questionId = question.getQuestion_id();
            Integer correctAnswer = correctAnswerMap.get(questionId);
            userSelectedQuestionsMap.put(questionId,selectedAnswerContent);
            adapter.notifyItemChanged(questionPosition);
            if (correctAnswer != null && selectedAnswerId == correctAnswer) {
                score++;
            }
        }
}
