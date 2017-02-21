package com.healthcare.Fragments.module_vaccination;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.healthcare.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllVaccineReminders extends Fragment {


    public AllVaccineReminders() {
        // Required empty public constructor
    }


    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_all_vaccine_reminders, container, false);

        return  v;
    }

}
