package com.healthcare.Fragments.module_doctor_chat;

/**
 * Created by guptaanirudh100 on 3/3/2017.
 */

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthcare.Activities.MainActivity;
import com.healthcare.Fragments.module_vaccination.VaccineFragment;
import com.healthcare.R;
import com.healthcare.handlers.DBHandler;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> implements
        TextToSpeech.OnInitListener  {

    private static final int MESSAGE_SENT = 1;
    private static final int MESSAGE_RECEIVED = 0;
    private List<Chat> chatList;
    private Context context;
    private String userName;
    private boolean imgFlag=false;
    private TextToSpeech tts;

    public ChatAdapter(List<Chat> chatList, Context context) {
        this.chatList = chatList;
        this.context = context;
        userName=DBHandler.getUserName(context);
    }

    @Override public ChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_message_sent, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.chat_message_received, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        final Chat chat = chatList.get(position);
        if(imgFlag){
            holder.imgMessage.setVisibility(ImageView.VISIBLE);
            holder.txtMessage.setVisibility(TextView.GONE);
            Picasso.with(context).load(chat.getMessage()).centerCrop().resize(300,300).placeholder(R.mipmap.ic_launcher).into(holder.imgMessage);
            holder.imgMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ((MainActivity) context).getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, ShowImage.newInstance(chat.getMessage()))
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
        else {
            holder.txtMessage.setVisibility(TextView.VISIBLE);
            holder.imgMessage.setVisibility(ImageView.GONE);
            holder.txtMessage.setText(chat.getMessage());
            holder.txtMessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //speakOut(chat.getMessage());
                }
            });
        }

    }

    private void speakOut(String msg) {
        tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);

    }

    @Override public int getItemCount() {
        return chatList.size();
    }

    @Override public int getItemViewType(int position) {
        if (chatList.get(position).getAuthor().equals(userName)) {
            if(chatList.get(position).isImage)
                imgFlag=true;
            else
                imgFlag =false;
            return MESSAGE_SENT;
        }
        if(chatList.get(position).isImage)
            imgFlag=true;
        else
            imgFlag =false;

        return MESSAGE_RECEIVED;
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } /*else {
                btnSpeak.setEnabled(true);
                speakOut();
            }*/

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtMessage;
        ImageView imgMessage;

        public ViewHolder(View view) {
            super(view);
            txtMessage = (TextView) view.findViewById(R.id.txt_message);
            imgMessage=(ImageView)view.findViewById(R.id.img_message);
        }
    }
}
