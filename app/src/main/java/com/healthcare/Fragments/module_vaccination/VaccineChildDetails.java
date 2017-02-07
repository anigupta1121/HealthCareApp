package com.healthcare.Fragments.module_vaccination;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.healthcare.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class VaccineChildDetails extends Fragment {


    ImageButton btnDate;
    TextView childDOBTV;
    Button btnAddChild, newChildBtn;

    public VaccineChildDetails() {
        // Required empty public constructor
    }

    ListView vaccinesListView;
    ArrayList<VaccineDetails> vaccineDetails = new ArrayList<>();
    ArrayList<String> vaccineName = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_vaccine_child_details, container, false);
        btnAddChild = (Button) v.findViewById(R.id.addVaccine);
        newChildBtn = (Button) v.findViewById(R.id.newChildBtn);

        btnDate = (ImageButton) v.findViewById(R.id.btnDatePick);
        vaccinesListView = (ListView) v.findViewById(R.id.vaccinesList);

        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, vaccineName);

        childDOBTV = (TextView) v.findViewById(R.id.textView40);
        btnAddChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(getContext());
                View promptsView = li.inflate(R.layout.vaccine_prompt, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        getContext());

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);
                final EditText etName = (EditText) promptsView
                        .findViewById(R.id.etVaccineName);
                final EditText etType = (EditText) promptsView
                        .findViewById(R.id.etVaccineType);
                final EditText etDate = (EditText) promptsView
                        .findViewById(R.id.etDate);

                etDate.setOnClickListener(new View.OnClickListener() {
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

                                etDate.setText(sdf.format(myCalendar.getTime()));
                            }

                        };
                        new DatePickerDialog(getContext(), date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();


                    }
                });


                alertDialogBuilder
                        .setTitle("Vaccine Details")
                        .setCancelable(false)
                        .setPositiveButton("Save",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        vaccineName.add(etName.getText().toString());
                                        vaccineDetails.add(new VaccineDetails(etName.getText().toString(),
                                                etDate.getText().toString(), etType.getText().toString()));
                                        updateList();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

        btnDate.setOnClickListener(new View.OnClickListener() {
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
                        updateLabel(myCalendar);

                    }

                };
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });

        vaccinesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                vaccineName.remove(position);
                vaccineDetails.remove(position);
                updateList();
                return true;
            }
        });
        return v;
    }

    private void updateList() {
        adapter.notifyDataSetChanged();
        vaccinesListView.setAdapter(adapter);
        if (vaccineDetails.size() > 0) {

            newChildBtn.setEnabled(true);
        } else {
            newChildBtn.setEnabled(false);
        }
    }


    private void updateLabel(Calendar myCalendar) {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Log.d("timeDate", sdf.format(myCalendar.getTime()));
        childDOBTV.setText("DOB:" + sdf.format(myCalendar.getTime()));
    }

}
