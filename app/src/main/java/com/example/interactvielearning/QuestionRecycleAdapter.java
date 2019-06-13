package com.example.interactvielearning;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class QuestionRecycleAdapter extends RecyclerView.Adapter<QuestionRecycleAdapter.ViewHolder> {

    private ArrayList<Question> mQuestionList;
    private Context mContext;

    private RadioButton radioButton;

    public QuestionRecycleAdapter(Context context, ArrayList<Question> questionList) {
        mContext = context;
        mQuestionList = questionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_question_item, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.title.setText(mQuestionList.get(i).getTitle());
        viewHolder.limitTime.setMax(mQuestionList.get(i).getLimitTime());
        viewHolder.choice1.setText(mQuestionList.get(i).getChoice1());
        viewHolder.choice2.setText(mQuestionList.get(i).getChoice2());
        viewHolder.choice3.setText(mQuestionList.get(i).getChoice3());
        viewHolder.choice4.setText(mQuestionList.get(i).getChoice4());



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference currentQuestion = database.getReference("quizQuestions").child(mQuestionList.get(i).questionName);

        final int[] timeCounter = {mQuestionList.get(i).getLimitTime()};
        final Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                timeCounter[0]--;

                viewHolder.limitTimeText.setText(" " + (Integer.toString(timeCounter[0]) + " Seconds left, before time's up"));
                viewHolder.limitTime.setProgress(timeCounter[0]);

                if (timeCounter[0] <= 0) {
                    timer.cancel();
                    currentQuestion.child("activated").setValue(false);
                    currentQuestion.child("isDone").setValue(true);

                    sendToQuizFragment();
                }
            }
        },0,1000);

        viewHolder.quizSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.radioChoice.getCheckedRadioButtonId() != -1) {
                    int selectedId = viewHolder.radioChoice.getCheckedRadioButtonId();
                    radioButton = viewHolder.itemView.findViewById(selectedId);

                    if (((String) radioButton.getText()).equals(mQuestionList.get(i).getCorrectAnswer())) {
                        currentQuestion.child("correct").setValue(true);
                    }

                    Toast.makeText(mContext, "You have submitted the quiz already!", Toast.LENGTH_SHORT).show();

                    timer.cancel();
                    currentQuestion.child("activated").setValue(false);
                    currentQuestion.child("isDone").setValue(true);
                    sendToQuizFragment();
                } else {
                    Toast.makeText(mContext, "Please choose the answer!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void sendToQuizFragment() {
        QuizFragment quizFragment= new QuizFragment();

        FragmentManager fragmentManager = ((AppCompatActivity)mContext).getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, quizFragment, null)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView limitTimeText;
        ProgressBar limitTime;
        RadioGroup radioChoice;
        RadioButton choice1;
        RadioButton choice2;
        RadioButton choice3;
        RadioButton choice4;
        Button quizSubmitButton;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.question_title);
            limitTimeText = itemView.findViewById(R.id.limitTimeText);
            limitTime = itemView.findViewById(R.id.limitTime);
            radioChoice = itemView.findViewById(R.id.radioChoice);
            choice1 = itemView.findViewById(R.id.choice1);
            choice2 = itemView.findViewById(R.id.choice2);
            choice3 = itemView.findViewById(R.id.choice3);
            choice4 = itemView.findViewById(R.id.choice4);
            quizSubmitButton = itemView.findViewById(R.id.quizSubmitButton);
        }
    }
}
