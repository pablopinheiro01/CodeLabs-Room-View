package br.com.part.codelabs.job

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import br.com.part.codelabs.R
import br.com.part.codelabs.add.ui.TaskAddActivity

class NotificationsWorkManager(context: Context, parameters: WorkerParameters ): Worker(context, parameters) {

    private val PRIMARY_CHANNEL_ID = "primary_channel_id"
    private val NOTIFICATION_ID = 0

    private var notificationWorkManager: NotificationManager? = null

    override fun doWork(): Result {
        val name = inputData.getString(TaskAddActivity.SCHEDULE_EXTRA_TASK_NAME)

        return name?.let{
            createChannel()
            sendNotification(it)
            Result.success()
        } ?: Result.failure()

    }

    private fun createChannel(){
        //create notification manager object
        notificationWorkManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationsChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                applicationContext.getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationsChannel.enableLights(true)
            notificationsChannel.lightColor = Color.RED
            notificationsChannel.enableVibration(true)
            notificationsChannel.description = applicationContext.getString(R.string.notification_channel_description)
        }
    }

    private fun sendNotification(name: String){
        val notificationBuilder: NotificationCompat.Builder = getNotificationBuilder(name)
        notificationWorkManager?.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun getNotificationBuilder(name: String): NotificationCompat.Builder {
        return NotificationCompat.Builder(applicationContext, PRIMARY_CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setContentTitle(name)
            .setContentText(applicationContext.getText(R.string.notification_text))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
    }


}