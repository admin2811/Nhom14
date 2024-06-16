package com.englishtlu.english_learning.main.dictionary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.dictionary.model.Definitions;
import com.englishtlu.english_learning.main.dictionary.viewHolder.DefinitionViewHolder;

import java.util.List;

public class DefinitionAdapter extends RecyclerView.Adapter<DefinitionViewHolder>
{
    private Context context;
    private List<Definitions> definitionsList;

    public DefinitionAdapter(Context context, List<Definitions> definitionsList) {
        this.context = context;
        this.definitionsList = definitionsList;
    }

    @NonNull
    @Override
    public DefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DefinitionViewHolder(LayoutInflater.from(context).inflate(R.layout.definitions_list_items,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull DefinitionViewHolder holder, int position) {
        holder.textView_definition.setText("Definition: " + definitionsList.get(position).getDefinition());
        holder.textView_example.setText("Example: "+ definitionsList.get(position).getExample());
        StringBuilder antonyns = new StringBuilder();
        StringBuilder synonyns = new StringBuilder();

        synonyns.append(definitionsList.get(position).getSynonyns());
        antonyns.append(definitionsList.get(position).getAntonyns());

        holder.textView_synonyns.setText(synonyns);
        holder.textView_antonyns.setText(antonyns);

        holder.textView_antonyns.setSelected(true);
        holder.textView_synonyns.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return definitionsList.size();
    }
}