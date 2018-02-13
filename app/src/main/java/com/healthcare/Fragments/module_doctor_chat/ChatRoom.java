package com.healthcare.Fragments.module_doctor_chat;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.healthcare.Activities.MainActivity;
import com.healthcare.R;
import com.healthcare.handlers.DBHandler;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatRoom extends Fragment {


    FirebaseStorage storageRef = FirebaseStorage.getInstance();
    StorageReference childRef=storageRef.getReference();
    String p="https://firebasestorage.googleapis.com/v0/b/healthcare-27168.appspot.com";
    private static final int RESULT_LOAD_IMAGE = 100;
    private static final int STORAGE_PERMISSION_CODE = 23;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    EditText etMessage;
    FloatingActionButton btnSend;
    String Username, myUsername;
    long phoneFriend, myPhone;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference chatRef = database.getReference().child("chats");
    DatabaseReference myRef = database.getReference().child("users");
    ChatAdapter adapter;
    ArrayList<Chat> chatList = new ArrayList<>();
    SwipeRefreshLayout refreshLayout;
    private final int REQ_CODE_SPEECH_INPUT = 110;

    public static ChatRoom newInstance(String username, String phone) {
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        bundle.putString("phone", phone);
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setArguments(bundle);
        return chatRoom;
    }

    public ChatRoom() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragm    ent
        View v = inflater.inflate(R.layout.fragment_chat_room, container, false);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading Image");

        Bundle bundle = getArguments();
        Username = bundle.getString("username");
        phoneFriend = Long.parseLong(bundle.getString("phone"));
        myPhone = Long.parseLong(DBHandler.getPhone(getContext())+"");
        myUsername = DBHandler.getUserName(getContext());
        setupToolbar();
        etMessage = (EditText) v.findViewById(R.id.etMessage);
        btnSend = (FloatingActionButton) v.findViewById(R.id.btnSend);
        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeChat);
        recyclerView = (RecyclerView) v.findViewById(R.id.chatRecycler);
        adapter = new ChatAdapter(chatList, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        btnImageChange();

        refreshLayout.setEnabled(false);
        refreshLayout.setRefreshing(true);
        getData();

        etMessage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (etMessage.getRight() - etMessage.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        Toast.makeText(getContext(), "Choose from gallery!!", Toast.LENGTH_SHORT).show();
                        if(checkPermission()) {
                            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, RESULT_LOAD_IMAGE);
                        }else{
                            askForPermission();
                        }
                        return true;
                    }
                }
                return false;
            }
        });
       /* refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });*/


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etMessage.getText().toString().equals("")) {
                    promptSpeechInput();
                } else {
                    sendMessage(etMessage.getText().toString());
                }
                etMessage.setText("");
            }
        });
        return v;
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void askForPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == STORAGE_PERMISSION_CODE){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
                Toast.makeText(getContext(),"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(getContext(),"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean checkPermission() {
        int permissionCheck = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            permissionCheck = ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        return permissionCheck == PackageManager.PERMISSION_GRANTED;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_CODE_SPEECH_INPUT&&resultCode==RESULT_OK&&null!=data){
            ArrayList<String> result = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            etMessage.setText(result.get(0));
        }
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
                && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContext().getContentResolver().query(
                    selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();


            Bitmap bmp = BitmapFactory.decodeFile(filePath);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 20, baos);
//this will convert image to byte[]
            byte[] byteArrayImage = baos.toByteArray();
// this will convert byte[] to string
          //  String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);

            uploadImg(byteArrayImage);

        }
    }
    private void uploadImg(byte[] byteArrayImage) {

        final UploadTask uploadTask = childRef.child("images/"+(phoneFriend+myPhone)+"/"+System.currentTimeMillis())
                .putBytes(byteArrayImage);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(
                        getContext());
                alertDialogBuilder.
                        setTitle("Cancel upload!")
                        .setPositiveButton("Yes",new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                uploadTask.cancel();
                            }
                        })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                // create alert dialog
                android.app.AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });
        progressDialog.show();
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getContext(),"Failed!Try again",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Log.d("Upload:",exception.toString());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
               String downloadUrl = String.valueOf(taskSnapshot.getMetadata().getDownloadUrl());
                sendMessage(downloadUrl);
                Log.d("urlimg:",downloadUrl);
                progressDialog.dismiss();
            }
        });

        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                long progress = (int)(100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                progressDialog.setMessage(progress+"%");
            }
        });
    }

    private void getData() {
        chatList.removeAll(chatList);
        chatRef.child((phoneFriend + myPhone) + "").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String msg;
                for (DataSnapshot k : dataSnapshot.getChildren()) {
                    msg=k.getValue().toString();
                    if(msg.contains(p)) {
                        chatList.add(new Chat(msg, k.getKey(), true));

                    }
                    else {
                        chatList.add(new Chat(msg, k.getKey(), false));

                    }
                }

                adapter.notifyDataSetChanged();

                refreshLayout.setRefreshing(false);
                recyclerView.scrollToPosition(chatList.size() - 1);
                Log.d("chatList:", chatList.toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        recyclerView.setAdapter(adapter);
    }

    private void sendMessage(String msg) {
        Log.d("phone:", phoneFriend + myPhone + "");
        chatRef.child((phoneFriend + myPhone) + "").child(System.currentTimeMillis() + "").child(myUsername).setValue(msg);
        myRef.child(DBHandler.getPushId(getContext()))
                .child("chats").child(Username).child("lastMsg").setValue(msg);
    }

    private void btnImageChange() {
        etMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etMessage.getText().toString().equals("")) {
                    btnSend.setImageResource(R.drawable.ic_keyboard_voice_black_24dp);
                } else {
                    btnSend.setImageResource(R.drawable.ic_send_black_24dp);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
    }

    private void setupToolbar() {
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(Username.toUpperCase());
        }
    }

}
