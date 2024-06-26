package com.englishtlu.english_learning.main.flashcard.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.flashcard.LearnFlashCardActivity;

import java.util.List;

public class ListDeskAdapter extends RecyclerView.Adapter<ListDeskAdapter.MyViewHolder>{
    public Context context;
    private List<String> deskList;
    public ListDeskAdapter(Context context,List<String> deskList){
        this.context = context;
        this.deskList = deskList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.desk_list, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setData(position);
    }
    @Override
    public int getItemCount() {
        return deskList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        carbon.widget.TextView nameDesk;
        carbon.widget.LinearLayout getDesk;
        MyViewHolder(@NonNull View itemView){
            super(itemView);
            nameDesk = itemView.findViewById(R.id.deskName);
            getDesk = itemView.findViewById(R.id.getDesk);
        }
        private void setData(final int pos){
            nameDesk.setText(deskList.get(pos));
            getDesk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LearnFlashCardActivity.class);
                    intent.putExtra("nameDesk",deskList.get(pos));
                    context.startActivity(intent);
                }
            });
        }

    }
}
