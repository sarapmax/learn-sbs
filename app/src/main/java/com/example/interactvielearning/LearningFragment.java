package com.example.interactvielearning;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

public class LearningFragment extends Fragment {

    private RecyclerView lessonListRecucleView;

    private ArrayList<Lesson> lessonList;
    LessonRecycleAdapter lessonAdapter;

    private ProgressBar learningProgressBar;
    private ProgressBar lessonProgression;
    private TextView lessonProgressionNumber;

    private int doneLessonNumber;

//    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_learning, container, false);

        learningProgressBar = rootView.findViewById(R.id.learningProgressBar);
        lessonProgression = rootView.findViewById(R.id.lessonProgression);
        lessonProgressionNumber = rootView.findViewById(R.id.lessonProgressionNumber);

        lessonListRecucleView = rootView.findViewById(R.id.lesson_list_recycle_view);

        lessonList = new ArrayList<>();

        lessonAdapter = new LessonRecycleAdapter(getActivity(), lessonList);
        lessonListRecucleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        lessonListRecucleView.setAdapter(lessonAdapter);
        lessonListRecucleView.setHasFixedSize(true);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("courseLessons").child("webDesign");

        learningProgressBar.setVisibility(View.VISIBLE);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot lessonSnapShot: dataSnapshot.getChildren()) {
                    Lesson lesson = lessonSnapShot.getValue(Lesson.class).withLessonName(lessonSnapShot.getKey());

                    lessonList.add(lesson);

                    if (lesson.isDone) {
                        doneLessonNumber++;
                    }

                    lessonAdapter.notifyDataSetChanged();
                }

                double progressionNumber;

                progressionNumber = (int)(((double)doneLessonNumber/(double)lessonList.size()) * 100);

                lessonProgression.setProgress((int) progressionNumber);
                lessonProgressionNumber.setText((int)progressionNumber + "%");

                learningProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });

//        mDatabase = FirebaseDatabase.getInstance().getReference("courseLessons");
//
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot lessonSnapShot: dataSnapshot.getChildren()) {
//                    Lesson lesson = lessonSnapShot.getValue(Lesson.class);
//
//                    Log.i("lesson title", lesson.getTitle());
//
//                    lessonList.add(lesson);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


//        firebaseFireStore = Fi
//        CardView cardView = rootView.findViewById(R.id.site_structure);
//
//        cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LessonFragment nextFrag= new LessonFragment();
//
//                getFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container, nextFrag, null)
//                        .addToBackStack(null)
//                        .commit();
//            }
//        });
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");



        return rootView;
    }
}
