package com.healthcare.Fragments.module_doctor_chat;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.healthcare.R;
import com.healthcare.handlers.DBHandler;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecentChats extends Fragment {


    SwipeRefreshLayout recentSwipeLayout;
    RecyclerView recyclerView;
    ArrayList<RecentChatInfo> chatInfo=new ArrayList<>();
    RecentChatAdapter adapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
   DatabaseReference chatRef;
    public RecentChats() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_recent_chats, container, false);
        chatRef=database.getReference().child("users").child(DBHandler.getPushId(getContext())).child("friends");
        recentSwipeLayout=(SwipeRefreshLayout)v.findViewById(R.id.recentSwipeLayout);
        recyclerView=(RecyclerView)v.findViewById(R.id.recent_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recentSwipeLayout.setRefreshing(true);
        getData();
        recentSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try{
                getData();}
                catch (Exception e){
                    Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return v;
    }

    private void getData() {

        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatInfo.removeAll(chatInfo);
                for(DataSnapshot k:dataSnapshot.getChildren()){
                    chatInfo.add(new RecentChatInfo(k.getKey(),k.getValue().toString()));
                }
                adapter=new RecentChatAdapter(getContext(),chatInfo);
                recyclerView.setAdapter(adapter);
                recentSwipeLayout.setRefreshing(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
