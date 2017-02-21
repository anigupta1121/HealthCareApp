package com.healthcare.Fragments.module_family_record;

import android.app.Dialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.healthcare.R;

import java.util.ArrayList;

/**
 * Created by Ashish on 19-Feb-17.
 */
public class fam_frag1 extends Fragment
{
    RecyclerAdapter1 adapter1;
    RecyclerView recyclerView;
    ArrayList<String> I;
    MyFamilyDBHandler handler;
    Button button,ok,cancel;
    EditText editText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_fam_frag1,container,false);
        recyclerView=(RecyclerView) view.findViewById(R.id.rv);
        button=(Button)view.findViewById(R.id.addb);
        handler=new MyFamilyDBHandler(getContext(),null,null,1);
        I=handler.displayf();
        for(int i=0;i<I.size();i++)
        {
            System.out.println(I.get(i)+"\ndfs");
        }
        adapter1=new RecyclerAdapter1(I,getContext());
        recyclerView.swapAdapter(adapter1,true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_layout);
                dialog.setTitle("Enter Member Name");
                editText=(EditText)dialog.findViewById(R.id.dialogtext);
                ok=(Button)dialog.findViewById(R.id.diabadd);
                cancel=(Button)dialog.findViewById(R.id.diabcancel);
                dialog.show();
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String s=editText.getText().toString();
                            if(s!=""||s!=null) {
                                handler.addRow1(s);
                                dialog.dismiss();
                                fam_frag1 fragment1 = new fam_frag1();
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.container, fragment1, "One");
                                fragmentTransaction.commit();
                            }
                        else
                            {
                                Toast.makeText(getContext(), "Please Fill Member Name", Toast.LENGTH_SHORT).show();
                            }
                    }
                });
            }
        });

        return view;
    }
}
