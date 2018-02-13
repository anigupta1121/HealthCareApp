package com.healthcare.Fragments.module_vaccination;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthcare.R;

import java.util.ArrayList;

/**
 * Created by guptaanirudh100 on 2/21/2017.
 */

public class AllVaccineAdapter extends RecyclerView.Adapter<AllVaccineAdapter.MyHolder> {

    Context context;

    ArrayList<VaccineDetails> vaccineDetails;
    public AllVaccineAdapter(Context context, ArrayList<VaccineDetails> vaccineDetails) {
        this.context = context;
        this.vaccineDetails=vaccineDetails;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(context).inflate(R.layout.all_reminders_adapter,parent,false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        holder.vaccineName.setText(vaccineDetails.get(position).vaccine_name);
        holder.tvType.append(vaccineDetails.get(position).type);
        holder.tvDueDate.setText(vaccineDetails.get(position).date);
        if(vaccineDetails.get(position).given.equals("yes")){
            holder.imageGiven.setImageResource(R.drawable.ic_check_black_24dp);
        }


    }

    @Override
    public int getItemCount() {
        return vaccineDetails.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView vaccineName,tvDueDate,tvGivenDate,tvType;
        ImageView imageGiven;
        public MyHolder(View itemView) {
            super(itemView);
            vaccineName=(TextView)itemView.findViewById(R.id.vaccineName);
            imageGiven=(ImageView)itemView.findViewById(R.id.imageGiven);
            tvDueDate=(TextView)itemView.findViewById(R.id.tvDueDate);
            tvGivenDate=(TextView)itemView.findViewById(R.id.tvGivenDate);
            tvType=(TextView)itemView.findViewById(R.id.tvType);


        }
    }
}
