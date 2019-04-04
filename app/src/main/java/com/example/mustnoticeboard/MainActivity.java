package com.example.mustnoticeboard;
//SSEGUJJA JIMMY
//2017/BIT/163

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private  int DELAY_TIME=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hidong the action bar on the main activity
        getSupportActionBar().hide();

        //Seting the delay method
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        },DELAY_TIME);

    }
}
