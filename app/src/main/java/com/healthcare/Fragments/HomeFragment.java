package com.healthcare.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.healthcare.Activities.MainActivity;
import com.healthcare.Fragments.module_doctor_chat.ChatFrag;
import com.healthcare.Fragments.module_doctor_chat.ChatTabs;
import com.healthcare.Fragments.module_family_record.fam_frag1;
import com.healthcare.Fragments.module_pill_reminder.PillFragment;
import com.healthcare.Fragments.module_vaccination.VaccineFragment;
import com.healthcare.Fragments.module_vaccination.VaccineRecyclerAdapter;
import com.healthcare.Fragments.module_visitScheduler.FragDocVisit;
import com.healthcare.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    GridView gridView;

    public HomeFragment() {
        // Required empty public constructor
    }


    private static final Integer[] imgs = {R.drawable.vaccine_grid, R.drawable.pills_grid, R.drawable.family_grid,
            R.drawable.visit_grid, R.drawable.ambulance_grid, R.drawable.doctor_grid};
    private static final String[] title = {"VACCINATION RECORD", "PILL REMINDER", "FAMILY MEDICAL RECORD", "VISIT SCHEDULER"
            , "AMBULANCE", "DOCTOR CHAT"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        gridView = (GridView) v.findViewById(R.id.gridHome);

        MyAdapter adapter = new MyAdapter();
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        ((MainActivity) getContext()).getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, new VaccineFragment())
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 1:
                        ((MainActivity) getContext()).getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, new PillFragment())
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 2:
                        ((MainActivity) getContext()).getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, new fam_frag1())
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 3:
                        ((MainActivity) getContext()).getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, new FragDocVisit())
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .addToBackStack(null)
                                .commit();
                        break;
                    case 4:
                        break;
                    case 5:
                        ((MainActivity) getContext()).getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, new ChatTabs())
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .addToBackStack(null)
                                .commit();
                        break;
                }
            }
        });

        return v;
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View gridView;
            if (convertView == null) {

                //gridView = new View(getContext());

                gridView = inflater.inflate(R.layout.grid_layout, parent, false);


                TextView textView = (TextView) gridView
                        .findViewById(R.id.gridTitle);
                ImageView imageView = (ImageView) gridView.findViewById(R.id.gridImage);
                textView.setText(title[position]);
                imageView.setImageResource(imgs[position]);


            } else {

                gridView = (View) convertView;
            }

            return gridView;
        }


    }
}
