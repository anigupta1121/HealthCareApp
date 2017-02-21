package com.healthcare.Fragments.module_vaccination;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by guptaanirudh100 on 2/21/2017.
 */

public class AllVaccineAdapter extends RecyclerView.Adapter<AllVaccineAdapter.MyHolder> {

    Context context;
    public AllVaccineAdapter(Context context){
        this.context=context;
    }
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        public MyHolder(View itemView) {
            super(itemView);
        }
    }
}
