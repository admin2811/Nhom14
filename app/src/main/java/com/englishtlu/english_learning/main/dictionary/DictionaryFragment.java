package com.englishtlu.english_learning.main.dictionary;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.dictionary.adapters.MeaningAdapter;
import com.englishtlu.english_learning.main.dictionary.adapters.PhoneticsAdapter;
import com.englishtlu.english_learning.main.dictionary.model.APIResponse;

import java.util.ArrayList;
import java.util.Locale;

public class DictionaryFragment extends Fragment {

    private SearchView searchView;
    private ImageView micIV;
    private TextView textViewWord;
    private RecyclerView recyclerViewPhonetics, recyclerViewMeanings;
    private ProgressDialog progressDialog;
    private PhoneticsAdapter phoneticsAdapter;
    private MeaningAdapter meaningAdapter;
    private static final int REQUEST_PERMISSION_CODE = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dictionary, container, false);

        searchView = view.findViewById(R.id.search_view);
        micIV = view.findViewById(R.id.mic_icon);
        textViewWord = view.findViewById(R.id.textView_word);
        recyclerViewPhonetics = view.findViewById(R.id.recycle_phonetics);
        recyclerViewMeanings = view.findViewById(R.id.recycle_meanings);
        progressDialog = new ProgressDialog(getActivity());

        progressDialog.setTitle("Loading...");
        progressDialog.show();

        RequestManager manager = new RequestManager(getActivity());
        manager.getWordMeaning(listener, "hello");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressDialog.setTitle("Fetching response for " + query);
                progressDialog.show();
                RequestManager manager = new RequestManager(getActivity());
                manager.getWordMeaning(listener, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        micIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something to translate");
                try {
                    startActivityForResult(intent, REQUEST_PERMISSION_CODE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), " " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PERMISSION_CODE && resultCode == getActivity().RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && result.size() > 0) {
                String spokenText = result.get(0);
                textViewWord.setText(spokenText);
            }
        }
    }

    private final OnFetchDataListener listener = new OnFetchDataListener() {
        @Override
        public void onFetchData(APIResponse apiResponse, String message) {
            progressDialog.dismiss();
            if (apiResponse == null) {
                Toast.makeText(getActivity(), "No data found!!", Toast.LENGTH_SHORT).show();
                return;
            }
            showData(apiResponse);
        }

        @Override
        public void onError(String message) {
            progressDialog.dismiss();
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    };

    @SuppressLint("SetTextI18n")
    private void showData(APIResponse apiResponse) {
        textViewWord.setText("word: " + apiResponse.getWord());
        recyclerViewPhonetics.setHasFixedSize(true);
        recyclerViewPhonetics.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        phoneticsAdapter = new PhoneticsAdapter(getActivity(), apiResponse.getPhonetics());
        recyclerViewPhonetics.setAdapter(phoneticsAdapter);

        recyclerViewMeanings.setHasFixedSize(true);
        recyclerViewMeanings.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        meaningAdapter = new MeaningAdapter(getActivity(), apiResponse.getMeanings());
        recyclerViewMeanings.setAdapter(meaningAdapter);
    }
}
