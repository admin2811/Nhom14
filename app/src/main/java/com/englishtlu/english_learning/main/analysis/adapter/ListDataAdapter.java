package com.englishtlu.english_learning.main.analysis.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.analysis.model.Progresses;
import com.englishtlu.english_learning.main.flashcard.adapter.ListCardAdapter;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.List;

import carbon.Carbon;
import carbon.widget.ProgressBar;

public class ListDataAdapter extends RecyclerView.Adapter<ListDataAdapter.MyViewHolder> {
    Context context;
    List<Progresses> tests;
    public ListDataAdapter(Context context, List<Progresses> tests){
        this.context = context;
        this.tests = tests;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_list, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setData(position);
    }
    @Override
    public int getItemCount() {
        return tests.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTestName, tvProgressText;
        CircularProgressBar barProgress;
        MyViewHolder(@NonNull View itemView){
            super(itemView);
            tvTestName = itemView.findViewById(R.id.tvTestName);
            tvProgressText = itemView.findViewById(R.id.tvProgressText);
            barProgress = itemView.findViewById(R.id.barProgress);
        }
        private void setData(final int pos) {
            tvTestName.setText(tests.get(pos).getNameTest());
            int process = (Integer.parseInt(tests.get(pos).getNumtrue())*100)/(tests.get(pos).getLenQuiz());
            Toast.makeText(context,Integer.toString(process),Toast.LENGTH_LONG).show();
            barProgress.setProgress(process);
            barProgress.setVisibility(View.VISIBLE);
            tvProgressText.setText(Integer.toString(process) + "%");
        }
    }

}
