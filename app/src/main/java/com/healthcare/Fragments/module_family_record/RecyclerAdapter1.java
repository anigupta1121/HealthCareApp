package com.healthcare.Fragments.module_family_record;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.healthcare.R;

import java.util.ArrayList;

/**
 * Created by Ashish on 20-Feb-17.
 */
public class RecyclerAdapter1 extends RecyclerView.Adapter<RecyclerAdapter1.MyViewHolder1> {

    ArrayList<String> I;
    Context context;
    LayoutInflater inflater;
    Bundle bundle;
    MyFamilyDBHandler handler;

    public RecyclerAdapter1(ArrayList<String> I,Context context) {
        this.I=I;
        this.context=context;
        this.inflater=LayoutInflater.from(context);
        bundle=new Bundle();
        handler=new MyFamilyDBHandler(context,null,null,1);
    }

    @Override
    public MyViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.rv1_layout,parent,false);
        MyViewHolder1 holder1=new MyViewHolder1(view);
        return holder1;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder1 holder, final int position) {
        final String current=I.get(position);
        holder.t.setText(current);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(context, holder.relativeLayout);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().toString().equals("View"))
                        {
                            Intent In=new Intent(context,Vaccine.class);
                            bundle.putString("naam",current);
                            In.putExtras(bundle);
                            context.startActivity(In);

                        }
                        else
                        {
                            handler.deleteRow1(current);
                            I.remove(position);
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
        return I.size();
    }

    class MyViewHolder1 extends RecyclerView.ViewHolder
    {
        TextView t;
        RelativeLayout relativeLayout;
        public MyViewHolder1(View itemView) {
            super(itemView);
            t=(TextView)itemView.findViewById(R.id.textview);
            relativeLayout=(RelativeLayout)itemView.findViewById(R.id.iteml);
        }
    }
}
