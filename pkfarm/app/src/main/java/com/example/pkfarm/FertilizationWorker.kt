package com.example.pkfarm

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class FertilizationWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    override fun doWork(): Result {
        sendNotification()
        return Result.success()
    }

    private fun sendNotification() {
        val notification = NotificationCompat.Builder(applicationContext, "fertilization_reminder")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("🌱 Fertilization Reminder")
            .setContentText("Time to fertilize your avocado seedlings!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        NotificationManagerCompat.from(applicationContext).notify(1, notification)
    }
}
