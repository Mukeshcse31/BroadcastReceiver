package com.tutorials.broadcastreceiver

import android.app.Activity
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import com.tutorials.broadcastreceiver.MainViewModel.Constants.ACTIVITY_BOOLEAN
import com.tutorials.broadcastreceiver.MainViewModel.Constants.ACTIVITY_STRING
import com.tutorials.broadcastreceiver.MainViewModel.Constants.CONTEXT_BOOLEAN
import com.tutorials.broadcastreceiver.MainViewModel.Constants.CONTEXT_STRING

class MainViewModel : ViewModel() {

    lateinit var context: Context
    lateinit var activity: Activity

    fun setContext(
        context: Context,
        activity: Activity
    ) {
        this.context = context
        this.activity = activity
    }

    fun sharedPrefActions(
        str: String,
        bool: Boolean
    ) {
        sharedPreferences(str, bool)
        getPreferences(str, bool)
        readSharedPreferences()
    }

    private fun sharedPreferences(str: String, bool: Boolean) {
        val sp = context.getSharedPreferences("app", ComponentActivity.MODE_PRIVATE)
        sp.edit().apply {
            putString(CONTEXT_STRING, str)
            putBoolean(CONTEXT_BOOLEAN, bool)
        }.apply()
    }

    private fun getPreferences(str: String, bool: Boolean) {
        // Context.MODE_PRIVATE, Context.MODE_WORLD_READABLE, Context.MODE_WORLD_WRITEABLE, Context.MODE_MULTI_PROCESS
        val gp = activity.getPreferences(ComponentActivity.MODE_PRIVATE)
        gp.edit().apply {
            putString(ACTIVITY_STRING, str)
            putBoolean(ACTIVITY_BOOLEAN, bool)
        }.apply()

    }

    private fun readSharedPreferences() {
        val sp = context.getSharedPreferences("app", ComponentActivity.MODE_PRIVATE)
        val str = sp.getString(CONTEXT_STRING, "")
        val bool = sp.getBoolean(CONTEXT_BOOLEAN, false)
        println(
            "readSharedPreferences: \n" +
                    "$CONTEXT_STRING = $str \n" +
                    "$CONTEXT_BOOLEAN = $bool \n"
        )

        val gp = activity.getPreferences(ComponentActivity.MODE_PRIVATE)
        val gpStr = gp.getString(ACTIVITY_STRING, "")
        val gpBool = gp.getBoolean(ACTIVITY_BOOLEAN, false)
        println(
            "getPreferences: \n" +
                    "$ACTIVITY_STRING = $gpStr \n" +
                    "$ACTIVITY_BOOLEAN = $gpBool \n"
        )
    }

//    fun encryption() {
//        val masterKeys = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
//        val esp = EncryptedSharedPreferences.create(
//            "sfvsf",
//            masterKeys,
//            context,
//            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//        )
//        esp.edit().apply {
//            putString("username", "Mukesh")
//            putString("password", "password")
//        }.apply()
//
//        println("encryption: the username is ${esp.getString("username", "default")}")
//        println("encryption: the password is ${esp.getString("password", "default")}")
//    }

    object Constants {
        const val CONTEXT_STRING = "SHARED_PREFERENCES_STRING"
        const val CONTEXT_BOOLEAN = "SHARED_PREFERENCES_BOOLEAN"
        const val ACTIVITY_STRING = "GET_PREFERENCES_STRING"
        const val ACTIVITY_BOOLEAN = "GET_PREFERENCES_BOOLEAN"
    }
}