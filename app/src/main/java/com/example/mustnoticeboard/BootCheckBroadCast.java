package com.example.mustnoticeboard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class BootCheckBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            Toast.makeText(context,"System Booting has been made successfully",Toast.LENGTH_LONG).show();
        }
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            Toast.makeText(context,"Some changes in intenet coonection \nhave been made succesffull",Toast.LENGTH_LONG).show();
        }
    }
}
