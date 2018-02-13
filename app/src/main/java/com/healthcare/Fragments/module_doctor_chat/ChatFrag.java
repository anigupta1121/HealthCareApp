package com.healthcare.Fragments.module_doctor_chat;


import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.internal.SnackbarContentLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.healthcare.Activities.MainActivity;
import com.healthcare.R;
import com.healthcare.handlers.DBHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFrag extends Fragment {


    Button btnStartChat;
    EditText etDoctorUserName;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference().child("users");
    String userName = "",myUsername;
    Boolean flag=false;
    String phoneFriend;
    ProgressDialog progressDialog;

    public ChatFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chat, container, false);
        btnStartChat = (Button) v.findViewById(R.id.btnStartChat);
        etDoctorUserName = (EditText) v.findViewById(R.id.etDoctorUserName);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Searching...");
        myUsername=DBHandler.getUserName(getContext());

        setupToolbar();
        btnStartChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(etDoctorUserName.getText().toString().equals(myUsername)||etDoctorUserName.getText().toString().equals(""))) {
                    progressDialog.show();
                    userName = etDoctorUserName.getText().toString().trim();
                    flag = false;
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot k : dataSnapshot.getChildren()) {

                                if (k.child("username").getValue().toString().equals(userName)) {
                                    flag = true;
                                    phoneFriend = k.child("phone").getValue().toString();
                                    setFriend(k.getKey(), userName);
                                }

                            }
                            if (!flag) {
                                Snackbar.make(getView(), "Not Found!", Snackbar.LENGTH_LONG)
                                        .setAction("Ok", null)
                                        .show();
                                progressDialog.dismiss();
                            }

                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }else {
                    Snackbar.make(getView(),"Invalid Username!",Snackbar.LENGTH_SHORT).show();
                }

            }
        });
        return v;
    }

    private void setFriend(String pushFriend,String userName) {
        String pushOwn=DBHandler.getPushId(getContext());
        myRef.child(pushFriend).child("friends").
                child(DBHandler.getUserName(getContext())).setValue(DBHandler.getPhone(getContext()));
        myRef.child(pushOwn).child("friends").child(userName).setValue(phoneFriend);


        progressDialog.dismiss();
        ((MainActivity) getContext()).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, ChatRoom.newInstance(userName,phoneFriend))
                .setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }


    private void setupToolbar() {
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("Doctor Chat");
        }
    }

}
