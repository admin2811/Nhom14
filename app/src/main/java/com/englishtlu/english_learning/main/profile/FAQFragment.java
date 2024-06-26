package com.englishtlu.english_learning.main.profile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.main.profile.adapter.FAQadapter;
import com.englishtlu.english_learning.main.profile.model.FAQ;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class FAQFragment extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;

    FAQadapter faQadapter;
    ArrayList<FAQ> faqList;

    ReviewManager reviewManager;
    ReviewInfo reviewInfo;
    Button btnReview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_f_a_q, container, false);
        ImageView back = view.findViewById(R.id.imageView7);
        recyclerView = view.findViewById(R.id.recyclerView);
        databaseReference = FirebaseDatabase.getInstance().getReference("FAQ");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        faqList = new ArrayList<>();
        faQadapter = new FAQadapter(getContext(), faqList);
        recyclerView.setAdapter(faQadapter);
        btnReview =view.findViewById(R.id.btnReview);
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RateUsDialog rateUsDialog = new RateUsDialog(requireContext());
                rateUsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                rateUsDialog.setCancelable(false);
                rateUsDialog.show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("FAQFragment", "Back button clicked");
                requireActivity().onBackPressed();
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FAQ faq = dataSnapshot.getValue(FAQ.class);
                    faqList.add(faq);
                }
                faQadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return view;
    }
    public void requestReviewInfo(){
        reviewManager = ReviewManagerFactory.create(requireContext());
        Task<ReviewInfo> request = reviewManager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                reviewInfo = task.getResult();
                showReviewFlow();
            }else{
                Toast.makeText(requireContext(),"Review not reviced",Toast.LENGTH_SHORT).show();
            }
        });
    }



}