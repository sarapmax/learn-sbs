package com.example.interactvielearning;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CourseFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_course, container, false);

        CardView cardView = rootView.findViewById(R.id.card_learn);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LearningFragment nextFrag= new LearningFragment();

                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFrag, null)
                        .addToBackStack(null)
                        .commit();
            }
        });

        CardView cardQuiz = rootView.findViewById(R.id.card_quiz);

        cardQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuizFragment nextFrag= new QuizFragment();

                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFrag, null)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return rootView;
    }
}
