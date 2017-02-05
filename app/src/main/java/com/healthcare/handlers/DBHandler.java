package com.healthcare.handlers;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by guptaanirudh100 on 2/3/2017.
 */

public class DBHandler {

    public static void clearDb(Context paramContext) {
        if (paramContext != null) {
            SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("UserDetails", 0).edit();
            localEditor.clear();
            localEditor.commit();
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

    public static String getName(Context context) {
        SharedPreferences name = context.getSharedPreferences("UserDetails", MODE_PRIVATE);
        return name.getString("name", "Name");
    }
}
