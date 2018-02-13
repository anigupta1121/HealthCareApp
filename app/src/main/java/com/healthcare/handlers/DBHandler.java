package com.healthcare.handlers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by guptaanirudh100 on 2/3/2017.
 */

public class DBHandler {

    public static void clearDb(Context paramContext) {
        if (paramContext != null) {
            SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("UserDetails", 0).edit();
            localEditor.clear();
            localEditor.apply();
        }
    }

    public static boolean isLoggedIn(Context context) {

        SharedPreferences loggedIn = context.getSharedPreferences("UserDetails", MODE_PRIVATE);
        return loggedIn.getBoolean("loggedIn", false);
    }

    public static void setLoggedIn(Boolean loggedIn, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("UserDetails", MODE_PRIVATE).edit();
        editor.putBoolean("loggedIn", loggedIn).apply();
    }

    public static void setName(String name, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("UserDetails", MODE_PRIVATE).edit();
        editor.putString("name", name).apply();
    }
    public static void setUserName(String userName, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("UserDetails", MODE_PRIVATE).edit();
        editor.putString("userName", userName).apply();
    }

    public static void setPushId(String push, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("UserDetails", MODE_PRIVATE).edit();
        Log.d("PushId:",push);
        editor.putString("pushID", push).apply();
    }
    public static void setPhone(String phone, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences("UserDetails", MODE_PRIVATE).edit();

        editor.putString("phone", phone).apply();
    }



    public static String getName(Context context) {
        SharedPreferences name = context.getSharedPreferences("UserDetails", MODE_PRIVATE);
        return name.getString("name", "Name");
    }

    public static String getUserName(Context context) {
        SharedPreferences name = context.getSharedPreferences("UserDetails", MODE_PRIVATE);
        return name.getString("userName", "userName");
    }

    public static String getPushId(Context context) {
        SharedPreferences name = context.getSharedPreferences("UserDetails", MODE_PRIVATE);
        Log.d("returnPush:",name.getString("pushID", "push"));
        return name.getString("pushID", "push");
    }

    public static String getPhone(Context context) {
        SharedPreferences name = context.getSharedPreferences("UserDetails", MODE_PRIVATE);
        return name.getString("phone", "phone");
    }
}
