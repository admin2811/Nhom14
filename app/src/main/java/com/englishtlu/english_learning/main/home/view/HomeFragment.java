package com.englishtlu.english_learning.main.home.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.englishtlu.english_learning.R;
import com.englishtlu.english_learning.authentication.model.User;
import com.englishtlu.english_learning.main.document.activity.PDFActivity;
import com.englishtlu.english_learning.main.flashcard.FlashCardActivity;
import com.englishtlu.english_learning.main.home.adapter.CourseAdapter;
import com.englishtlu.english_learning.main.home.model.Course;
import com.englishtlu.english_learning.main.quizz.QuizzVocabActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private static final String ARG_LAST_NAME = "LAST_NAME";
    private String lastName;
    public TextView LastName;
    public ProgressBar progressBar;

    EditText findcourse;

    public RecyclerView recyclerViewCourse;

    private CourseAdapter adapterPopular;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String lastName) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LAST_NAME, lastName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            lastName = getArguments().getString(ARG_LAST_NAME);
        }

    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Lay du liệu từ MainActivity2


        View view = inflater.inflate(R.layout.fragment_home2, container, false);
        LastName = view.findViewById(R.id.textView6);
        progressBar = view.findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.VISIBLE);
        findcourse = view.findViewById(R.id.editFind);

        // Kiểm tra xem đã có Bundle được truyền vào fragment chưa
        if (getArguments() != null && getArguments().containsKey(ARG_LAST_NAME)) {
            lastName = getArguments().getString(ARG_LAST_NAME);
            progressBar.setVisibility(View.GONE);
        }else{
            lastName = "Sir ";
            progressBar.setVisibility(View.GONE);
        }
        LastName.setText(lastName);

        findcourse.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (adapterPopular != null) {
                    adapterPopular.filterList(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        return view;
    }

    @SuppressLint("WrongViewCast")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewCourse = view.findViewById(R.id.view);
        ImageSlider imageSlider = (ImageSlider) view.findViewById(R.id.imageSlider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.image1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image3, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image3, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
        intRecyclerView();


    }



    private void intRecyclerView() {
        ArrayList<Course> ItemsArrayList = new ArrayList<>();

        ItemsArrayList.add(new Course(1,"vocabulary", "Vocabulary \n" +
                "Treasure Hunt", "Collect hidden English vocabulary words in a timed scavenger hunt", 4.5));
        ItemsArrayList.add(new Course(2,"completion", "Sentence Building Competition", "Drag and drop words to form grammatically correct sentences", 4.5));
        //ItemsArrayList.add(new Course("quiz", "Grammar Quiz", "Test your knowledge of English grammar with a multiple-choice quiz", 4.5));
        //flashcard
        ItemsArrayList.add(new Course(3,"flashcard", "FlashCard Revolution: Unleash Learning!", "Unveiling the Mind: Unlocking Secret Skills with Flashcards", 4.5));
        //document
        ItemsArrayList.add(new Course(4,"document", "Document Dynamo: Empower Knowledge", "Discover the power of effective document with \"Document Dynamics.\"", 4.5));

        recyclerViewCourse.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        // Truyền listener vào adapter
         adapterPopular = new CourseAdapter(ItemsArrayList, new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course) {
                Log.d("CourseAdapter", "ID: " + course.getId());
                //Khi ấn vào document thì chuyển sang PDFActivity
                if (course.getId() == 4) {
                    Intent intent = new Intent(requireContext(), PDFActivity.class);
                    startActivity(intent);
                }else if(course.getId() == 1){
                    Intent intent = new Intent(requireContext(), QuizzVocabActivity.class);
                    startActivity(intent);
                } else if (course.getId() == 3) {
                    Intent intent = new Intent(requireContext(), FlashCardActivity.class);
                    startActivity(intent);
                } else if (course.getId() == 2) {
                    Intent intent = new Intent(requireContext(), QuizzVocabActivity.class);
                    intent.putExtra("COURSE_ID", course.getId());
                    startActivity(intent);

                }
            }
        });
        recyclerViewCourse.setAdapter(adapterPopular);
    }
}