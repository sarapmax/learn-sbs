package com.example.interactvielearning;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class QuizResultRecycleAdapter extends RecyclerView.Adapter<QuizResultRecycleAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Question> mQuestionList;

    public QuizResultRecycleAdapter(Context context, ArrayList<Question> questionList) {
        mContext = context;
        mQuestionList = questionList;
    }

    @NonNull
    @Override
    public QuizResultRecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_quiz_result_item, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizResultRecycleAdapter.ViewHolder viewHolder, int i) {
        viewHolder.questionTitle.setText(mQuestionList.get(i).getTitle());
        viewHolder.correctAnswer.setText("Correct Answer: " + mQuestionList.get(i).getCorrectAnswer());
        viewHolder.correctAnswer.setTextColor(mContext.getResources().getColor(R.color.colorSuccess));

        if(mQuestionList.get(i).getCorrect()) {
            viewHolder.quizDoneImage.setBackgroundResource(R.drawable.ic_check_circle_black_72dp);
        } else {
            viewHolder.quizDoneImage.setBackgroundResource(R.drawable.ic_cancel_72dp);
        }
    }

    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView quizDoneImage;
        TextView questionTitle;
        TextView correctAnswer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            quizDoneImage = itemView.findViewById(R.id.quiz_done);
            questionTitle = itemView.findViewById(R.id.quiz_result_question_title);
            correctAnswer = itemView.findViewById(R.id.quiz_result_correct_answer);
        }
    }
}
