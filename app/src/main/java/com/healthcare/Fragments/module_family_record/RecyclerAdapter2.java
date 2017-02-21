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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.healthcare.R;

import java.util.ArrayList;

/**
 * Created by Ashish on 20-Feb-17.
 */
public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.MyViewHolder2> {

    ArrayList<MemberInfo> arrayList;
    Context context;
    LayoutInflater inflater;
    MyFamilyDBHandler handler;
    String na;

    public RecyclerAdapter2(ArrayList<MemberInfo> arrayList, Context context,String na) {
        this.arrayList = arrayList;
        this.context = context;
        this.inflater=LayoutInflater.from(context);
        handler=new MyFamilyDBHandler(context,null,null,1);
        this.na=na;
    }

    @Override
    public MyViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.rv2_layout,parent,false);
        MyViewHolder2 holder2=new MyViewHolder2(view);
        return holder2;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder2 holder, final int position) {
        final MemberInfo current=arrayList.get(position);
        holder.t1.setText(current.dis);
        holder.t2.setText(current.doc);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(context, holder.linearLayout);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().toString().equals("View"))
                        {
                            Intent intent=new Intent(context,ViewD.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("dakter",current.doc);
                            bundle.putString("maraj",current.dis);
                            bundle.putString("naam",na);
                            intent.putExtras(bundle);
                            context.startActivity(intent);
                        }
                        else
                        {
                            handler.deleteRow2(current.doc,current.dis,na);
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

    class MyViewHolder2 extends RecyclerView.ViewHolder {
        TextView t1,t2;
        LinearLayout linearLayout;
        public MyViewHolder2(View itemView) {
            super(itemView);
            linearLayout=(LinearLayout)itemView.findViewById(R.id.outerlayout);
            t1=(TextView)itemView.findViewById(R.id.tv1);
            t2=(TextView)itemView.findViewById(R.id.tv2);
        }
    }
}
