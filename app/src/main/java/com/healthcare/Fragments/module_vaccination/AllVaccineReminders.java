package com.healthcare.Fragments.module_vaccination;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.healthcare.R;
import com.healthcare.handlers.VaccinationDBHandler;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllVaccineReminders extends Fragment {

    VaccinationDBHandler handler;
    ArrayList<VaccineDetails> vaccineDetails = new ArrayList<>();

    public static AllVaccineReminders newInstance(String name) {
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        AllVaccineReminders reminders = new AllVaccineReminders();
        reminders.setArguments(bundle);

        return reminders;
    }

    public AllVaccineReminders() {
        // Required empty public constructor
    }

    String name;

    RecyclerView recyclerView;
    AllVaccineAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_all_vaccine_reminders, container, false);
        //Toast.makeText(getContext(), "All Vaccines", Toast.LENGTH_SHORT).show();
        handler = new VaccinationDBHandler(getContext());
        Bundle bundle = getArguments();
        recyclerView=(RecyclerView)v.findViewById(R.id.all_vaccine_reminder_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (bundle != null) {
            name = bundle.getString("name");
            Log.d("Vaccine Name::", name);
            getData(name);

            adapter = new AllVaccineAdapter(getContext(), vaccineDetails);
            Log.d("Vaccine Name::", vaccineDetails.size() + "");
            recyclerView.setAdapter(adapter);


        }


        return v;
    }

    private void getData(String name) {
        Cursor cursor = handler.getVaccinesByName(name);
        if(cursor!=null){
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {
            Log.d("All vaccines::",cursor.getString(cursor.getColumnIndex("vaccine_name")));
            vaccineDetails.add(new VaccineDetails(
                    cursor.getString(cursor.getColumnIndex("vaccine_name")),
                    cursor.getString(cursor.getColumnIndex("vaccination_date")),
                    cursor.getString(cursor.getColumnIndex("vaccination_type")),
                    cursor.getString(cursor.getColumnIndex("vaccination_given")))
            );
            cursor.moveToNext();
        }

        }else {
            Toast.makeText(getContext(),"No Vaccines Found",Toast.LENGTH_LONG).show();
        }
    }

}
