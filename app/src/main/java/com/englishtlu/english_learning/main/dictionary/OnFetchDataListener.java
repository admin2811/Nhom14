package com.englishtlu.english_learning.main.dictionary;


import com.englishtlu.english_learning.main.dictionary.model.APIResponse;

public interface OnFetchDataListener {
    void onFetchData(APIResponse apiResponse, String message);
    void onError(String message);
}