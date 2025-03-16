package com.example.pkfarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Create Notification Channel
        createNotificationChannel()

        // Schedule Fertilization Reminder
        scheduleFertilizationReminder()

        setContent {
             MaterialTheme(
        colorScheme = lightColorScheme(),
        typography = Typography,
        content = { PkFarmApp() }
    )
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "fertilization_reminder",
                "Fertilization Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Reminds you to fertilize your avocado seedlings."
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PkFarmApp() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val dateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("pkfarm - Planting Calendar") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Select a date to check planting advice", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            DatePicker(onDateSelected = { date -> selectedDate = date })

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Selected Date: ${selectedDate.format(dateFormatter)}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            PlantingAdvice(selectedDate)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(onDateSelected: (LocalDate) -> Unit) {
    val datePickerState = rememberDatePickerState()
    
    DatePickerDialog(
        onDismissRequest = {},
        confirmButton = {
            TextButton(onClick = {
                val selectedMillis = datePickerState.selectedDateMillis ?: return@TextButton
                onDateSelected(LocalDate.ofEpochDay(selectedMillis / 86400000))
            }) {
                Text("Select Date")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
fun PlantingAdvice(date: LocalDate) {
    val plantingMonths = listOf(Month.FEBRUARY, Month.APRIL, Month.SEPTEMBER)
    val isGoodMonth = plantingMonths.contains(date.month)

    Card(
        modifier = Modifier.padding(16.dp),
        colors = if (isGoodMonth) CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        else CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
    ) {
        Text(
            text = if (isGoodMonth) "🌱 Great time to plant avocado seedlings!" else "⚠ Not an ideal month for planting.",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

private fun scheduleFertilizationReminder() {
    val workRequest = PeriodicWorkRequestBuilder<FertilizationWorker>(
        14, TimeUnit.DAYS // Runs every 2 weeks
    ).build()

    WorkManager.getInstance(this).enqueueUniquePeriodicWork(
        "fertilization_reminder",
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest
    )
}
