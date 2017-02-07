package com.healthcare.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.healthcare.R;
import com.healthcare.handlers.DBHandler;

public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread timer=new Thread(){
            public void run(){
                try {
                    sleep(5000);

                }catch (InterruptedException e){
                    e.printStackTrace();

                }finally{
                    Intent intent;
                    if (DBHandler.isLoggedIn(getApplicationContext()))
                        intent = new Intent(SplashActivity.this, MainActivity.class);
                    else
                        intent = new Intent(SplashActivity.this, LoginActivity.class);

                    startActivity(intent);

                }
            }

        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
