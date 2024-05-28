package com.englishtlu.english_learning.main.chatbot.model;

import androidx.annotation.NonNull;

import com.englishtlu.english_learning.main.chatbot.ResponeCallBack;
import com.englishtlu.english_learning.main.chatbot.config.BuildConfig;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.BlockThreshold;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.ai.client.generativeai.type.GenerationConfig;
import com.google.ai.client.generativeai.type.HarmCategory;
import com.google.ai.client.generativeai.type.SafetySetting;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Collections;
import java.util.concurrent.Executor;

public class Message {
    public static String SENT_BY_ME = "me";
    public static String SENT_BY_BOT = "bot";
    String message;
    String sentBy;

    public Message(String message, String sentBy) {
        this.message = message;
        this.sentBy = sentBy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSentBy() {
        return sentBy;
    }

    public void setSentBy(String sentBy) {
        this.sentBy = sentBy;
    }

    public void getReponse(String question, ResponeCallBack callBack){
        GenerativeModelFutures model = getModel();

        Content content = new Content.Builder().addText(question).build();
        Executor executor = Runnable::run;
        ListenableFuture<GenerateContentResponse> respone = model.generateContent(content);
        Futures.addCallback(respone, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String answer = result.getText();
                callBack.onResponse(answer);
            }

            @Override
            public void onFailure(@NonNull Throwable t) {
                t.printStackTrace();
                callBack.onError(t);
            }
        },executor);
    }
    private GenerativeModelFutures getModel(){
        String apiKey = BuildConfig.apiKey;

        SafetySetting harassmentSafety = new SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.ONLY_HIGH);
        GenerationConfig.Builder configBuilder = new GenerationConfig.Builder();
        configBuilder.temperature=0.9f;
        configBuilder.topK = 16;
        configBuilder.topP = 0.1f;
        GenerationConfig generationConfig =configBuilder.build();

        GenerativeModel gm = new GenerativeModel(
                "gemini-1.5-flash",
                apiKey,
                generationConfig,
                Collections.singletonList(harassmentSafety)
        );
        return GenerativeModelFutures.from(gm);
    }
}
