package com.englishtlu.english_learning.main.chatbot;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.englishtlu.english_learning.main.chatbot.adapter.MessageAdapter;
import com.englishtlu.english_learning.main.chatbot.model.Message;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.englishtlu.english_learning.R;

import java.util.ArrayList;
import java.util.List;

public class ChatBotActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView welcomeTextView;
    EditText messageEditText;
    ImageButton sendButton,backButton;
    List<Message> messagesList;
    MessageAdapter messageAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);

        recyclerView = findViewById(R.id.recycler_view);
        messageEditText = findViewById(R.id.message_edit_text);
        sendButton = findViewById(R.id.send_btn);
        messagesList = new ArrayList<>();
        backButton = findViewById(R.id.backtohome);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //setup recycle view
        messageAdapter = new MessageAdapter(messagesList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = messageEditText.getText().toString().trim();

                messageEditText.setText("");
                addToChat(question,Message.SENT_BY_ME);
                CallBot(question);


            }
        });
    }

    public void addToChat(String message,String sentBy){
        runOnUiThread(new Runnable() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void run() {
                messagesList.add(new Message(message,sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }
    public void CallBot(String answer){
        Message message = new Message(answer,Message.SENT_BY_BOT);
        message.getReponse(answer, new ResponeCallBack() {
            @Override
            public void onResponse(String response) {
                addToChat(response,Message.SENT_BY_BOT);
            }

            @Override
            public void onError(Throwable throwable) {
                addToChat("Error",Message.SENT_BY_BOT);
            }
        });
    }
}