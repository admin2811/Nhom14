package com.englishtlu.english_learning.main.quizz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.quizz.adapter.LessonAdapter;
import com.englishtlu.english_learning.main.quizz.model.LessonVocab;

import java.util.ArrayList;

public class QuizzVocabActivity extends AppCompatActivity {

    ImageView ivBackToHome;
    public RecyclerView recyclerView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);
        ivBackToHome = findViewById(R.id.ivBackToHome);
        recyclerView = findViewById(R.id.rvQuizzVocab);
        ivBackToHome.setOnClickListener(v -> {
            finish();
        });
      intRecyclerView();
    }
   private void intRecyclerView() {
        ArrayList<LessonVocab> ItemsArrayList = new ArrayList<>();

        ItemsArrayList.add(new LessonVocab(1,"lesson1","Lesson Video", "20 Lesson Video of Vocabulary"));
        ItemsArrayList.add(new LessonVocab(2, "lessons2"," Practice Vocabulary", "20 Lesson Exam of Vocabulary"));
        ItemsArrayList.add(new LessonVocab(3, "lesson1", "Test Vocabulary", "20 Lesson Video of Vocabulary"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        RecyclerView.Adapter<LessonAdapter.ViewHolder> adapter = new LessonAdapter(ItemsArrayList, new LessonAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(LessonVocab lessonVocab){
                if(lessonVocab.getId() == 2){
                    Intent intent = new Intent(QuizzVocabActivity.this, PractieActivity.class);
                    startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

}