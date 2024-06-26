package com.englishtlu.english_learning.main.quizz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.MainActivity;
import com.englishtlu.english_learning.main.Video.VideoActivity;
import com.englishtlu.english_learning.main.quizz.adapter.LessonAdapter;
import com.englishtlu.english_learning.main.quizz.model.LessonVocab;
import com.englishtlu.english_learning.main.test.CourseActivity;

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
            Intent intent = new Intent(QuizzVocabActivity.this, MainActivity.class);
            startActivity(intent);
        });
      intRecyclerView();
      //Lấy intent được truyền từ Home
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
                }else if (lessonVocab.getId() == 1){
                    Intent intent = new Intent(QuizzVocabActivity.this, VideoActivity.class);
                    //Truyền tiếp courseId
                    int courseId = getIntent().getIntExtra("COURSE_ID", -1);
                    intent.putExtra("COURSE_ID", courseId);
                    startActivity(intent);
                }
                else if(lessonVocab.getId() == 3){
                    Intent intent = new Intent(QuizzVocabActivity.this, CourseActivity.class);
                    startActivity(intent);
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

}