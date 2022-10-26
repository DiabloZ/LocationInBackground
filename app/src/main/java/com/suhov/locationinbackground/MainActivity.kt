package com.suhov.locationinbackground

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.suhov.locationinbackground.ui.theme.LocationInBackgroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            0
        )
        setContent {
            LocationInBackgroundTheme {
                DefaultPreview(
                    onStartClick = { switchService(LocationService.ACTION_START) },
                    onStopClick = { switchService(LocationService.ACTION_STOP) }
                )
            }
        }
    }

    private fun switchService(action: String){
        val intent = Intent(applicationContext, LocationService::class.java)
        intent.action = action
        startService(intent)
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview(onStartClick: () -> Unit = {}, onStopClick: () -> Unit = {}) {
    LocationInBackgroundTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Button(onClick = onStartClick) {
                Text(text = "Start")
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = onStopClick) {
                Text(text = "Stop")
            }
        }
    }
}