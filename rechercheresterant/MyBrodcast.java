package com.example.amira.rechercheresterant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Amira on 03/09/2018.
 */

public class MyBrodcast extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        // check the action equal to the action we fire in broadcast,
        if   (   intent.getAction().equalsIgnoreCase("com.example.Broadcast"))
        //read the data from the intent
        {
            NewMessageNotification.notify(context, bundle.getString("msg"), 123);
            Toast.makeText(context, bundle.getString("msg"), Toast.LENGTH_LONG).show();
        }
    }
}
