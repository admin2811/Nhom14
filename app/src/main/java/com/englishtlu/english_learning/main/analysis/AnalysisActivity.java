package com.englishtlu.english_learning.main.analysis;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.analysis.adapter.ListDataAdapter;
import com.englishtlu.english_learning.main.analysis.model.Progresses;
import com.englishtlu.english_learning.main.test.model.Question;
import com.englishtlu.english_learning.main.test.repository.QuizRepository;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AnalysisActivity extends AppCompatActivity {
    BarChart barChart;
    FirebaseAuth auth;
    String userId;
    ArrayList<BarEntry> entries;
    List<String> xvalues;
    QuizRepository quizRepository;
    ListDataAdapter listDataAdapter;
    RecyclerView listProcesses;
    List<Progresses> progresses;
    private ArrayList<Question> questionList;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_analysis);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        back = findViewById(R.id.btnRecall);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        barChart = findViewById(R.id.chartBar);
        listProcesses = findViewById(R.id.Processlist);
        barChart.getAxisRight().setDrawLabels(false);

        auth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();
        }

        quizRepository = new QuizRepository(this);



        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("QuizResult").child(userId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i = 0;
                xvalues = new ArrayList<>();
                if(snapshot.exists()){
                    for (DataSnapshot childSnapshot : snapshot.getChildren()){
                        String idquiz = childSnapshot.getKey();
                        String quizname = childSnapshot.child("namequiz").getValue(String.class);
                        String result = childSnapshot.child("result").getValue(String.class);
                        progresses.add(new Progresses(quizname,result,quizRepository.readQuiz(idquiz).size()));
                        xvalues.add(quizname);
                        entries.add(new BarEntry(i,Integer.parseInt(result)));
                        Log.e("data",Integer.toString(quizRepository.readQuiz(idquiz).size()));
                        i++;
                    }
                    setAxisLeft();

                    BarDataSet barDataSet = new BarDataSet(entries,"Tests");
                    barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

                    BarData barData = new BarData(barDataSet);
                    barChart.setData(barData);

                    barChart.getDescription().setEnabled(false);
                    barChart.invalidate();

                    barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xvalues));
                    barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                    barChart.getXAxis().setGranularity(1f);
                    barChart.getXAxis().setGranularityEnabled(true);

                    listDataAdapter = new ListDataAdapter(AnalysisActivity.this,progresses);

                    listProcesses.setAdapter(listDataAdapter);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(AnalysisActivity.this);
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    listProcesses.setLayoutManager(layoutManager);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setAxisLeft(){
        YAxis yAxis = barChart.getAxisLeft();
        yAxis.setAxisMaximum(0f);
        yAxis.setAxisMaximum(20f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);
    }

}