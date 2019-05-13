package com.example.mustnoticeboard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.BatteryManager;
import android.widget.Toast;
import android.widget.Toolbar;

public class MyInternetConnection extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        boolean test=intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,false);
        if (test){
            Toast.makeText(context,"This app need internet connection for you to read \nNews online please turn on data or WI-FI",Toast.LENGTH_SHORT).show();
        }
        else {

            Toast.makeText(context,"Internet connection has be established successfully\nYou can now access your new from online",Toast.LENGTH_SHORT).show();

        }
    }
}
