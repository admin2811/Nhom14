package com.englishtlu.english_learning.main.quizz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.quizz.adapter.CategoryAdapter;
import com.englishtlu.english_learning.main.quizz.model.Category;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PractieActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private ImageView ivBackToHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practie);
        recyclerView = findViewById(R.id.rvPracticeVocab);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        ivBackToHome = findViewById(R.id.ivBackToHome);
        ivBackToHome.setOnClickListener(v -> {
            finish();
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://nhom14-android.000webhostapp.com/test/fetch_categories.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Category>> call = apiService.getCategories();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    List<Category> categories = response.body();
                    assert categories != null;
                    for (Category category : categories) {
                        Log.d("PractieActivity", "Category - Name: " + category.getName() + ", Description: " + category.getDescription() + ", Image URL: " + category.getImage());
                    }
                    categoryAdapter = new CategoryAdapter(PractieActivity.this, categories, new CategoryAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Category category) {
                            if(category.getId() == 1){
                                Intent intent = new Intent(PractieActivity.this, StartActivity.class);
                                intent.putExtra("CATEGORY_ID", category.getId());
                                startActivity(intent);
                            }
                        }
                    });
                    recyclerView.setAdapter(categoryAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable throwable) {
                Toast.makeText(PractieActivity.this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}