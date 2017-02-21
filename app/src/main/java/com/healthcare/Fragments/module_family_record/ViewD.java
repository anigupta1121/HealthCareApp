package com.healthcare.Fragments.module_family_record;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.healthcare.R;

public class ViewD extends AppCompatActivity {
    TextView t1,t2,t3,t4,t5,t6;
    MyFamilyDBHandler handler;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_d);
        t1=(TextView)findViewById(R.id.tt1);
        t2=(TextView)findViewById(R.id.tt2);
        t3=(TextView)findViewById(R.id.tt3);
        t4=(TextView)findViewById(R.id.tt4);
        t5=(TextView)findViewById(R.id.tt5);
        t6=(TextView)findViewById(R.id.tt6);
        handler=new MyFamilyDBHandler(this,null,null,1);
        bundle=getIntent().getExtras();
        Element element=handler.displaydesc(bundle.getString("dakter"),bundle.getString("maraj"),bundle.getString("naam"));
        t1.setText(element.get_doctor());
        t2.setText(element.get_date());
        t3.setText(element.get_place());
        t4.setText(element.get_disease());
        t5.setText(element.get_tests());
        t6.setText(element.get_prescription());
    }
}
