package com.healthcare.Fragments.module_visitScheduler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.healthcare.R;

import java.util.ArrayList;

/**
 * Created by Ashish on 31-Jan-17.
 */
public class RecycleVisitAdapter extends RecyclerView.Adapter<RecycleVisitAdapter.MyViewHolder>
{
    ArrayList<VisitInfo> arrayList;
    LayoutInflater inflater;
    Context context;
    MyDBHandler handler;
    Bundle bundle;

    public RecycleVisitAdapter(ArrayList<VisitInfo> arrayList, Context context) {
        this.inflater =LayoutInflater.from(context);
        this.arrayList=arrayList;
        this.context=context;
        handler=new MyDBHandler(context,null,null,1);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.rv_layout,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final VisitInfo current=arrayList.get(position);
        holder.en.setText(current.ename);
        holder.ed.setText(current.edate);
        holder.et.setText(current.etime);
        bundle=new Bundle();
        holder.l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(context, holder.l);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().toString().equals("View"))
                        {
                            if (current.ename!=null) {
                                bundle.putString("keyname", current.ename);
                            }
                            Intent I=new Intent(context,View_window.class);
                            I.putExtras(bundle);
                            context.startActivity(I);
                        }
                        else
                        {
                            handler.deleteRow(current.ename);
                            arrayList.remove(position);
                            notifyDataSetChanged();

                        }
                        return true;
                    }
                });

                popup.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView en,ed,et;
        LinearLayout l;
        public MyViewHolder(View itemView) {
            super(itemView);
            l=(LinearLayout)itemView.findViewById(R.id.lilay);
            en=(TextView)itemView.findViewById(R.id.ename);
            ed=(TextView)itemView.findViewById(R.id.edate);
            et=(TextView)itemView.findViewById(R.id.etime);
        }
    }
}
