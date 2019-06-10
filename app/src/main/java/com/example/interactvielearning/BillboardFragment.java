package com.example.interactvielearning;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class BillboardFragment extends Fragment {

    private RecyclerView billboardRecycleView;
    private BillboardRecycleAdapter billboardAdapter;
    private ProgressBar billboardPregressBar;
    private ArrayList<Announcement> annoucementList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_billboard, container, false);

        billboardRecycleView = rootView.findViewById(R.id.billboard_recycle_view);

        billboardPregressBar = rootView.findViewById(R.id.billboardProgressBar);

        annoucementList = new ArrayList<>();

        billboardAdapter = new BillboardRecycleAdapter(getActivity(), annoucementList);
        billboardRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        billboardRecycleView.setAdapter(billboardAdapter);
        billboardRecycleView.setHasFixedSize(true);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("announcements");

        billboardPregressBar.setVisibility(View.VISIBLE);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot questionSnapshot: dataSnapshot.getChildren()) {
                    Announcement announcement = questionSnapshot.getValue(Announcement.class);

                    annoucementList.add(announcement);

                    billboardAdapter.notifyDataSetChanged();
                }

                billboardPregressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return rootView;
    }
}
