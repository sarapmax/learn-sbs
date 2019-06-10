package com.example.interactvielearning;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LessonRecycleAdapter extends RecyclerView.Adapter<LessonRecycleAdapter.ViewHolder> {

    private ArrayList<Lesson> mLessonList;
    private Context mContext;

    public LessonRecycleAdapter(Context context, ArrayList<Lesson> lessonList) {
        mContext = context;
        mLessonList = lessonList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_lesson_item, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.title.setText(mLessonList.get(i).getTitle());

        if(mLessonList.get(i).getIsDone()) {
            viewHolder.isDoneImage.setBackgroundResource(R.drawable.ic_check_circle_black_24dp);
        } else {
            viewHolder.isDoneImage.setBackgroundResource(R.drawable.ic_access_time_black_24dp);
        }

        viewHolder.onClick(mLessonList.get(i));
    }

    @Override
    public int getItemCount() {
        return mLessonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView isDoneImage;
        CardView lessonCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            isDoneImage = itemView.findViewById(R.id.is_done);
            lessonCard = itemView.findViewById(R.id.lessonCard);
        }

        public void onClick(final Lesson lesson)
        {
            lessonCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                LessonFragment lessonFragment= new LessonFragment();

                FragmentManager fragmentManager = ((AppCompatActivity)mContext).getSupportFragmentManager();

                Bundle args = new Bundle();
                args.putString("title", lesson.getTitle());
                args.putBoolean("isDone", lesson.getIsDone());
                args.putString("content", lesson.getContent());
                args.putString("instruction", lesson.getInstruction());
                args.putString("code", lesson.getCode());
                args.putString("correctAnswer", lesson.getCorrectAnswer());
                args.putString("lessonName", lesson.lessonName);
                lessonFragment.setArguments(args);

                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, lessonFragment, null)
                        .addToBackStack(null)
                        .commit();
                }
            });
        }
    }
}
