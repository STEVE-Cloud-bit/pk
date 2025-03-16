package com.example.pkfarm.ui.screens

import android.widget.CalendarView
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun CalendarScreen() {
    var selectedDate by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { CalendarView(it) },
            update = { calendarView ->
                calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
                    selectedDate = "$dayOfMonth/${month + 1}/$year"
                }
            }
        )
    }
}
