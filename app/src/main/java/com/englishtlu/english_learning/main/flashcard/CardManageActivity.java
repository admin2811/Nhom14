package com.englishtlu.english_learning.main.flashcard;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.englishtlu.english_learning.R;

public class CardManageActivity extends AppCompatActivity {
    FrameLayout addCradFrame;
    Dialog addcarddialog;
    Dialog addeskDialog;
    Button addDeskbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_card_manage);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addDeskbtn = findViewById(R.id.buttonAddesk);

        addeskDialog = new Dialog(this);
        addeskDialog.setContentView(R.layout.dialog_box_desk);
        addeskDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        addeskDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.dialog_bg));
        addeskDialog.setCancelable(false);

        addDeskbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addeskDialog.show();
            }
        });
    }
}