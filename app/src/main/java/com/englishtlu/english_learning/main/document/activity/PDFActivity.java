package com.englishtlu.english_learning.main.document.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.MainActivity;
import com.englishtlu.english_learning.main.document.adapter.PDFAdapter;
import com.englishtlu.english_learning.main.document.model.PDF;
import com.englishtlu.english_learning.main.profile.DocActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PDFActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PDFAdapter pdfAdapter;
    private List<PDF> pdfList = new ArrayList<>();

    private ImageView ivBack,ivStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfactivity);

        recyclerView = findViewById(R.id.rvpdf);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        pdfAdapter = new PDFAdapter(pdfList,this);
        recyclerView.setAdapter(pdfAdapter);
        ivBack = findViewById(R.id.ivBack);
        ivStorage = findViewById(R.id.storageBoxImage);
         ivStorage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(PDFActivity.this, DocActivity.class);
                 startActivity(intent);
             }
         });
        ivBack.setOnClickListener(v -> finish());

        fetchData();

    }

    private void fetchData() {
        String url = "https://nhom14-android.000webhostapp.com/test/pdf.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        @SuppressLint("NotifyDataSetChanged") JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            PDF pdf = new PDF();
                            pdf.setId(jsonObject.getString("id"));
                            pdf.setName(jsonObject.getString("name"));
                            pdf.setPdf(jsonObject.getString("pdf"));
                            pdfList.add(pdf);
                        }
                        pdfAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(PDFActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()
        );

        requestQueue.add(jsonArrayRequest);
    }
}