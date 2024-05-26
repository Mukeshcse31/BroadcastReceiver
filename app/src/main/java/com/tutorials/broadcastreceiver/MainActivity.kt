package com.tutorials.broadcastreceiver

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.widget.ToggleButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.tutorials.broadcastreceiver.ui.theme.BroadcastReceiverTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val testBroadcastReceiver = TestBroadcastReceiver()
    private val airplaneMode = AirplaneMode()
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Intent()
        registerReceiver(testBroadcastReceiver, IntentFilter("TEST_ACTION"), RECEIVER_EXPORTED)
        registerReceiver(airplaneMode, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
        viewModel = MainViewModel()
        viewModel.setContext(context = this, activity = this)
//        viewModel.encryption()

        setContent {
            BroadcastReceiverTheme {
                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier
//                        .fillMaxWidth(1f)
//                        .height(50.dp),
//                    color = MaterialTheme.colorScheme.background
//                ) {
                Column(
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .fillMaxWidth(0.75f)
                        .fillMaxHeight(1f)
                ) {
                    sendBroadcast()
                    Button(onClick = ::sendEmail) {
                        Text(text = "Send Email")
                    }
                    Toggle(viewModel = viewModel)
                    AlertDialogCompose()
                    SnackbarDemo()
                }
//                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(testBroadcastReceiver)
        unregisterReceiver(airplaneMode)
    }

    @Composable
    private fun sendBroadcast() {
        Button(modifier = Modifier
            .height(40.dp),
            onClick = {
                sendBroadcast(Intent().apply {
                    action = "TEST_ACTION"
                })
            }) {
            Text(text = "Send Broadcast")

        }
        Spacer(modifier = Modifier.height(20.dp))
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SEND).apply {
//            action = Intent.ACTION_SEND
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("mukeshcse31@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Android App")
            putExtra(Intent.EXTRA_TEXT, "Android Developer Job")
        }
        startActivity(Intent.createChooser(intent, "Select app to send an email"))
    }
}

@Composable
fun Toggle(viewModel: MainViewModel) {

    val checked = remember { mutableStateOf(true) }
    Checkbox(checked = checked.value, onCheckedChange = { checked.value = it })
    Switch(checked = checked.value, onCheckedChange = { checked.value = it })

    Button(onClick = { viewModel.sharedPrefActions("String", checked.value) }) {
        Text(text = "Shared Preferences")
    }
    Spacer(modifier = Modifier.height(20.dp))
}

@Composable
fun AlertDialogCompose() {
    val showAlertDialog = remember { mutableStateOf(true) }

    Switch(checked = showAlertDialog.value, onCheckedChange = { showAlertDialog.value = it })
    Text("Alert Dialog")
    if (showAlertDialog.value) {

        AlertDialog(
            title = { Text(text = "Alert Title") },
            text = { Text(text = "Alert Dialog Text") },
            onDismissRequest = {
                showAlertDialog.value = false
            },
            confirmButton = {
                Button(onClick = {
                    showAlertDialog.value = false
                }) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                Button(onClick = {
                    showAlertDialog.value = false
                }) {
                    Text(text = "Cancel")
                }
            }
        )
    }
    Spacer(modifier = Modifier.height(20.dp))

    val showDialog = remember { mutableStateOf(true) }
    Switch(checked = showDialog.value, onCheckedChange = { showDialog.value = it })
    Text("Dialog")
    if (!showDialog.value) return

    Dialog(onDismissRequest = { showDialog.value = false }) {
        Column {
            Text(text = "This is a Dialog")
            Button(onClick = { showDialog.value = false }) {
                Text("Close")
            }
        }
    }
}

@Composable
fun SnackbarDemo() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { innerPadding ->
            var clickCount by remember { mutableStateOf(0) }
            Button(
                onClick = {
                    // show snackbar as a suspend function
                    scope.launch {
                        snackbarHostState.showSnackbar("Snackbar # ${++clickCount}")
                    }
                }
            ) {
                Text(
                    modifier = Modifier.padding(innerPadding),
                    text = "Show snackbar"
                )
            }
        }
    )
}

@Composable
fun SnackbarDemo1() {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            var clickCount by remember { mutableStateOf(0) }
            ExtendedFloatingActionButton(
                onClick = {
                    // show snackbar as a suspend function
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            "Snackbar # ${++clickCount}"
                        )
                    }
                }
            ) { Text("Show snackbar") }
        },
        content = { innerPadding ->
            Text(
                text = "Body content",
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .wrapContentSize()
            )
        }
    )
}