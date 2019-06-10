package com.example.interactvielearning;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

class BillboardRecycleAdapter extends RecyclerView.Adapter<BillboardRecycleAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Announcement> mAnnouncementList;

    public BillboardRecycleAdapter(Context context, ArrayList<Announcement> announcementList) {
        mContext = context;
        mAnnouncementList = announcementList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_announcement_item, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.title.setText(mAnnouncementList.get(i).getTitle());
        viewHolder.description.setText(mAnnouncementList.get(i).getDescription());
        viewHolder.date.setText(mAnnouncementList.get(i).getDate());
    }

    @Override
    public int getItemCount() {
        return mAnnouncementList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;
        TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.announcement_title);
            description = itemView.findViewById(R.id.announcement_description);
            date = itemView.findViewById(R.id.announcement_date);
        }
    }
}
