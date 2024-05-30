package com.englishtlu.english_learning.main.chatbot;

public interface ResponeCallBack {
    void onResponse(String response);

    void onError(Throwable throwable);
}
