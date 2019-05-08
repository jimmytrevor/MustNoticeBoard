package com.example.mustnoticeboard;
//SSEGUJJA JIMMY
//2017/BIT/163

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private  int DELAY_TIME=2000;
MyInternetConnection myInternetConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myInternetConnection=new MyInternetConnection();

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

    @Override
    protected void onStart() {
        super.onStart();
       registerReceiver(myInternetConnection,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
//       startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0774165087")));
//        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.google.com")));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(myInternetConnection);
    }

    public void connectionText(){
        try {
            URL url = new URL("http://www.android.com/");
            HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();

        }catch (Exception f){
            Toast.makeText(getApplicationContext(),f.getMessage(),Toast.LENGTH_SHORT).show();
        }finally {

        }
    }


}
