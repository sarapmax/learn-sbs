package com.example.interactvielearning;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class QuizResultFragment extends Fragment {

    private RecyclerView quizResultRecycleView;
    private QuizResultRecycleAdapter quizResultAdapter;
    private ProgressBar quizResultPregressBar;
    private ArrayList<Question> questionList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_quiz_result, container, false);

        quizResultRecycleView = rootView.findViewById(R.id.quiz_result_recycle_view);

        quizResultPregressBar = rootView.findViewById(R.id.quizResultProgressBar);

        questionList = new ArrayList<>();

        quizResultAdapter = new QuizResultRecycleAdapter(getActivity(), questionList);
        quizResultRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        quizResultRecycleView.setAdapter(quizResultAdapter);
        quizResultRecycleView.setHasFixedSize(true);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("quizQuestions");

        quizResultPregressBar.setVisibility(View.VISIBLE);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot questionSnapshot: dataSnapshot.getChildren()) {
                    Question question = questionSnapshot.getValue(Question.class).withQuestionName(questionSnapshot.getKey());

                    if (question.isDone) {
                        questionList.add(question);

                        quizResultAdapter.notifyDataSetChanged();
                    }
                }

                quizResultPregressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return rootView;
    }
}
