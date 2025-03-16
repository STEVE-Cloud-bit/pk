package com.example.pkfarm.workers

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

class ReminderWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        // TODO: Show a notification
        return Result.success()
    }
}

fun scheduleFertilizationReminder(context: Context) {
    val workRequest = PeriodicWorkRequestBuilder<ReminderWorker>(7, TimeUnit.DAYS)
        .setConstraints(
            Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build()
        )
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "FertilizationReminder",
        ExistingPeriodicWorkPolicy.KEEP,
        workRequest
    )
}
