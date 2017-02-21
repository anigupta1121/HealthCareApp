package com.healthcare.Fragments.module_family_record;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.healthcare.R;

import java.util.ArrayList;

public class Vaccine extends AppCompatActivity {
    RecyclerView recyclerView;
    Button button;
    ArrayList<MemberInfo> arrayList;
    MyFamilyDBHandler handler;
    String na;
    Bundle bundle;
    RecyclerAdapter2 adapter2;

    EditText e1,e2,e3,e4,e5;
    Button ok,cancel;
    Element e;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine);
        recyclerView=(RecyclerView)findViewById(R.id.rv2);
        button=(Button)findViewById(R.id.addtreatb);
        handler=new MyFamilyDBHandler(this,null,null,1);
        bundle=getIntent().getExtras();
        na=bundle.getString("naam");
        arrayList=handler.displayt(na);

        adapter2=new RecyclerAdapter2(arrayList,this,na);
        recyclerView.swapAdapter(adapter2,true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog=new Dialog(Vaccine.this);
                dialog.setContentView(R.layout.layout_fam_frag2);
                dialog.setTitle("Enter Details");
                e1=(EditText)dialog.findViewById(R.id.ed1);
                e2=(EditText)dialog.findViewById(R.id.ed2);
                e3=(EditText)dialog.findViewById(R.id.ed3);
                e4=(EditText)dialog.findViewById(R.id.ed4);
                e5=(EditText)dialog.findViewById(R.id.ed5);
                ok=(Button)dialog.findViewById(R.id.ok2);
                cancel=(Button)dialog.findViewById(R.id.cancel2);
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

                        if(e1!=null||e2!=null&&e3!=null&&e4!=null&&e5!=null) {
                            e = new Element(e4.getText().toString(), na, "", e1.getText().toString(), e2.getText().toString(), e5.getText().toString(), e3.getText().toString());
                            handler.addRow2(e);
                            dialog.dismiss();
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(Vaccine.this, "Please Fill Completely", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
