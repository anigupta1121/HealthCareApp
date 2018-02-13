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
public class PillRemiderAdd extends Fragment {


    public PillRemiderAdd() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pill_remider_add, container, false);

    }

}
