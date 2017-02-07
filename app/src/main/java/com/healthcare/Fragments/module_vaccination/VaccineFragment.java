package com.healthcare.Fragments.module_vaccination;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.healthcare.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VaccineFragment extends Fragment {

    Button btnAddChild;

    public VaccineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_vaccine, container, false);
        btnAddChild = (Button) v.findViewById(R.id.btnAddChild);

        btnAddChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null)
                        .replace(R.id.container, new VaccineChildDetails())
                        .setCustomAnimations(FragmentTransaction.TRANSIT_FRAGMENT_OPEN,
                                FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
            }
        });
        return v;
    }

}
