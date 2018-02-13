package com.healthcare.Fragments.module_doctor_chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healthcare.Activities.MainActivity;
import com.healthcare.R;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

/**
 * Created by guptaanirudh100 on 3/8/2017.
 */

public class RecentChatAdapter extends RecyclerView.Adapter<RecentChatAdapter.MyHolder> {

    ArrayList<RecentChatInfo> chatInfo;
    Context context;
    public RecentChatAdapter(Context context,ArrayList<RecentChatInfo> chatInfo){
        this.chatInfo=chatInfo;
        this.context=context;
    }
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.recent_chat_adapter,parent,false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final RecentChatInfo recentChatInfo=chatInfo.get(position);
        holder.tvUsername.setText(recentChatInfo.getUserName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, ChatRoom.newInstance(recentChatInfo.getUserName(),recentChatInfo.getPhone()))
                        .setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatInfo.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView tvUsername,tvLastChat;
        public MyHolder(View itemView) {
            super(itemView);
            tvUsername=(TextView)itemView.findViewById(R.id.tvUser);

        }
    }
}
