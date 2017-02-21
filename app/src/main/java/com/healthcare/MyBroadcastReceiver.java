package com.healthcare;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.widget.Toast;

/**
 * Created by guptaanirudh100 on 2/20/2017.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
/*
        mp=MediaPlayer.create(context, R.raw.alarm);
        mp.start();
        Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();*/

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        // Put here YOUR code.
        Toast.makeText(context, "Alarm !!!!!!!!!!", Toast.LENGTH_LONG).show(); // For example

        wl.release();
    }
}
