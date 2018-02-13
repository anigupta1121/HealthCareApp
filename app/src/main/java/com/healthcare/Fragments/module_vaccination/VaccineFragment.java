package com.healthcare.Fragments.module_vaccination;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.healthcare.Activities.MainActivity;
import com.healthcare.R;
import com.healthcare.handlers.VaccinationDBHandler;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class VaccineFragment extends Fragment {

    Button btnAddChild;


    public VaccineFragment() {
        // Required empty public constructor
    }

    RecyclerView vaccineRecycler;
    VaccinationDBHandler handler;
    VaccineRecyclerAdapter recyclerAdapter;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> dob = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_vaccine, container, false);
        btnAddChild = (Button) v.findViewById(R.id.btnAddChild);
        handler = new VaccinationDBHandler(getContext());
        vaccineRecycler = (RecyclerView) v.findViewById(R.id.vaccineRecycler);
      //  ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        vaccineRecycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        getData();

        btnAddChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null)
                        .replace(R.id.container, new VaccineChildDetails())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
            }
        });
        return v;
    }

    private void getData() {
        Cursor cursor = handler.getChildData();
        cursor.moveToFirst();
        if (cursor != null) {
            name.removeAll(name);
            dob.removeAll(dob);
            for (int i = 0; i < cursor.getCount(); i++) {

                name.add(cursor.getString(cursor.getColumnIndex("child_name")));
                dob.add(cursor.getString(cursor.getColumnIndex("child_dob")));

                cursor.moveToNext();
            }
            recyclerAdapter = new VaccineRecyclerAdapter(getContext(), name, dob);
            vaccineRecycler.setAdapter(recyclerAdapter);
        }

    }


}
