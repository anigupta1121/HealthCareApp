package com.healthcare.Fragments.module_pill_reminder;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.healthcare.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PillFragment extends Fragment {


    public PillFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_pill, container, false);

        return v;
    }

}
