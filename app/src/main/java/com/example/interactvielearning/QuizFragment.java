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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuizFragment extends Fragment {

    private RecyclerView questionListRecycleView;
    private ArrayList<Question> questionList;
    private QuestionRecycleAdapter questionAdapter;
    private ProgressBar quizProgressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_quiz, container, false);

        quizProgressBar = rootView.findViewById(R.id.quizProgressBar);


        questionListRecycleView = rootView.findViewById(R.id.question_list_recycle_view);

        questionList = new ArrayList<>();

        questionAdapter = new QuestionRecycleAdapter(getActivity(), questionList);
        questionListRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        questionListRecycleView.setAdapter(questionAdapter);
        questionListRecycleView.setHasFixedSize(true);

        final CardView quizEmpty = rootView.findViewById(R.id.quizEmpty);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("quizQuestions");

        quizProgressBar.setVisibility(View.VISIBLE);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot questionSnapshot: dataSnapshot.getChildren()) {
                    Question question = questionSnapshot.getValue(Question.class).withQuestionName(questionSnapshot.getKey());

                    if (question.activated) {
                        quizEmpty.setVisibility(View.INVISIBLE);

                        questionList.add(question);

                        questionAdapter.notifyDataSetChanged();
                    } else {
                        quizEmpty.setVisibility(View.VISIBLE);
                    }
                }

                quizProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return rootView;
    }
}
