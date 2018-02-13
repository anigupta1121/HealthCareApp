package com.healthcare.Fragments.module_doctor_chat;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.healthcare.Activities.MainActivity;
import com.healthcare.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatTabs extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;

    public ChatTabs() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_chat_tabs, container, false);

        tabLayout = (TabLayout) v.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) v.findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Recent Chats"));
        tabLayout.addTab(tabLayout.newTab().setText("Find Doctors"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        FragmentAdapterClass fragmentAdapter = new FragmentAdapterClass(getChildFragmentManager(),
                tabLayout.getTabCount());

        viewPager.setAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab LayoutTab) {

                viewPager.setCurrentItem(LayoutTab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab LayoutTab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab LayoutTab) {

            }
        });
        return v;
    }

     class FragmentAdapterClass extends FragmentStatePagerAdapter {

        int tabCount;
        FragmentAdapterClass(FragmentManager fm, int countTabs) {
            super(fm);
            this.tabCount = countTabs;

        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new RecentChats();
                case 1:
                    return new ChatFrag();
            }
            return null;
        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }


}
