package com.healthcare.Fragments.module_visitScheduler;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import android.widget.TimePicker;
import android.widget.Toast;

import com.healthcare.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Ashish on 01-Feb-17.
 */
public class AddFrag extends Fragment
{
    EditText e1,e2,e3;

   public String d,m,y,vdate,hour,min,vname,ti,location,desc;
    Button button;
    int h;
    MyDBHandler handler;
    TimePicker timePicker;
    Button dateButton,timeButton;




    public AddFrag() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_addfrag,container,false);
        String ap[]={"AM","PM"};
        final ArrayAdapter<String> ad=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,ap);
        e1=(EditText) view.findViewById(R.id.e1);
        e2=(EditText) view.findViewById(R.id.e2);
        e3=(EditText) view.findViewById(R.id.e3);
        dateButton=(Button)view.findViewById(R.id.dateButton);
        timeButton=(Button) view.findViewById(R.id.timeButton);


        handler=new MyDBHandler(getContext(),null,null,1);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar myCalendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "dd/MM/yyyy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                        dateButton.setText(sdf.format(myCalendar.getTime()));

                        vdate=sdf.format(myCalendar.getTime());
                    }

                };
                DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                datePickerDialog.show();
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                final int hour1 = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeButton.setText( selectedHour + ":" + selectedMinute);
                        hour=selectedHour+"";
                        min=selectedMinute+"";
                    }
                }, hour1, minute, true);//Yes 24 hour time

                mTimePicker.show();

            }
        });
        button=(Button)view.findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                vname=e1.getText().toString();

                location=e2.getText().toString();
                desc=e3.getText().toString();
                if(vname!=null&&vdate!=null&&hour!=null&&min!=null&&location!=null&&desc!=null) {
                    try {
                        Element element = new Element(vname, vdate, hour, min, location, desc);
                        handler.addRow(element);
                    } catch (Exception e) {

                    }
                }
                else
                {
                    Toast.makeText(getContext(), "Please Fill Complete Form", Toast.LENGTH_SHORT).show();
                }


                FragDocVisit fragment1 = new FragDocVisit();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container,fragment1,"One");
                fragmentTransaction.commit();

            }
        });

        return view;
    }
}
