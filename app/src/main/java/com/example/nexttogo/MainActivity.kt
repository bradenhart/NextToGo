package com.example.nexttogo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.nexttogo.ui.NextToGoApp
import com.example.nexttogo.ui.theme.NextToGoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NextToGoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    NextToGoApp()
                }
            }
        }
    }
}

