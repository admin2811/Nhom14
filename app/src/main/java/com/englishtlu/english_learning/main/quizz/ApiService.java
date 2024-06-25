package com.englishtlu.english_learning.main.quizz;

import com.englishtlu.english_learning.main.quizz.model.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("fetch_categories.php")
    Call<List<Category>> getCategories();
}
