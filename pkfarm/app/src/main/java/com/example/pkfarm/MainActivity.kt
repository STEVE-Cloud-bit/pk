package com.example.pkfarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.pkfarm.ui.screens.HomeScreen
import com.example.pkfarm.ui.theme.PkFarmTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PkFarmTheme {
                HomeScreen()
            }
        }
    }
}
