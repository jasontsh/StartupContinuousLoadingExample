package com.jasontsh.interviewkickstart.loadingstartupexample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class Autostart : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null) {
            val serviceIntent = Intent(context, LoadingService::class.java)
            context.startService(serviceIntent)
        }
    }
}