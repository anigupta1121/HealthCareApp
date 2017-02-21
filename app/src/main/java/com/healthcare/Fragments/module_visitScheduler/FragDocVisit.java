package com.healthcare.Fragments.module_visitScheduler;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.healthcare.R;

import java.util.ArrayList;

/**
 * Created by Ashish on 31-Jan-17.
 */
public class FragDocVisit extends Fragment
{
    RecyclerView recyclerView;
    Button button;
    ArrayList<VisitInfo> arrayList;
    RecycleVisitAdapter adapter;
    MyDBHandler handler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.doc_visit_frag,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.rv);
        handler=new MyDBHandler(getContext(),null,null,1);
        arrayList=handler.display();

        for(int i=0;i<arrayList.size();i++)
        {
            System.out.println(arrayList.get(i).ename+"\n");
        }

        adapter=new RecycleVisitAdapter(arrayList,getContext());
            recyclerView.swapAdapter(adapter,true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        button=(Button)view.findViewById(R.id.addb);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFrag fragment2 = new AddFrag();
                FragmentManager fragmentManager =getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.container,fragment2,"Two");
                fragmentTransaction.commit();

            }
        });
        return view;
    }


}
