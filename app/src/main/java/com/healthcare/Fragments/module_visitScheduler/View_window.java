package com.healthcare.Fragments.module_visitScheduler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.healthcare.R;

public class View_window extends AppCompatActivity {

    MyDBHandler handler;
    Bundle bundle;
    TextView t1,t2,t3,t4,t5;
    Element e;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view_window);
        bundle=getIntent().getExtras();
        handler=new MyDBHandler(this,null,null,1);
        t1=(TextView)findViewById(R.id.t1);
        t2=(TextView)findViewById(R.id.t2);
        t3=(TextView)findViewById(R.id.t3);
        t4=(TextView)findViewById(R.id.t4);
        t5=(TextView)findViewById(R.id.t5);
        e=handler.displayone(bundle.getString("keyname"));
        t1.setText(e.get_name());
        t2.setText(e._date);
        t3.setText(e.get_hour()+":"+e.get_minute());
        t4.setText(e.get_location());
        t5.setText(e.get_description());
    }
}
