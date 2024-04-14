package com.tutorials.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings

class AirplaneMode : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            //Method 1
            val airplaneOn = Settings.Global.getInt(
                context?.contentResolver,
                Settings.Global.AIRPLANE_MODE_ON
            ) != 0

            println("airplane mode is $airplaneOn")

            //Method 2
            val state = intent.getBooleanExtra("state", false)
            println("airplane mode by state is $state")
        }

        println("airplane mode Result code is $resultCode")
        println("airplane mode Result data is $resultData")
    }
}