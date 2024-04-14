package com.tutorials.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TestBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == "TEST_ACTION"){
            println("Received TEST_ACTION broadcast")
        }
    }
}