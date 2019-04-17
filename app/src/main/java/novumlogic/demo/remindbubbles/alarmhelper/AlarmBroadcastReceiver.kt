package novumlogic.demo.remindbubbles.alarmhelper

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import novumlogic.demo.remindbubbles.BubbleActivity
import novumlogic.demo.remindbubbles.MainActivity
import novumlogic.demo.remindbubbles.R
import java.util.*

class AlarmBroadcastReceiver : BroadcastReceiver() {

    val NOTIFICATION_CHANNEL_ID = "30001"

    override fun onReceive(context: Context, intent: Intent) {
        var reminderMessage: String? = ""
        var reminderSubMessage: String? = ""
        intent.getStringExtra("REMINDER_MESSAGE").let { reminderMessage = it }
        intent.getStringExtra("REMINDER_SUB_MESSAGE").let { reminderSubMessage = it }
        sendNotification(context, reminderMessage, reminderSubMessage)
    }

    private fun sendNotification(context: Context, reminderMessage: String? = null, reminderSubMessage : String? = null) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            context, 1, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        // Create bubble intent
        val target = Intent(context, BubbleActivity::class.java)
//        target.putExtra("REMINDER_MESSAGE", reminderMessage)
        val bubbleIntent = PendingIntent.getActivity(context, 0, target, 0)

        // Create bubble metadata
        val bubbleMetadata = Notification.BubbleMetadata.Builder()
            .setDesiredHeight(200)
            // Note: although you can set the icon is not displayed in Q Beta 2
            .setIcon(Icon.createWithResource(context, R.mipmap.ic_launcher_round))
            .setIntent(bubbleIntent)
            .setAutoExpandBubble(true)
            .setSuppressInitialNotification(false)
            .build()

        // Create notification
        val notificationBuilder = Notification.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setContentTitle(reminderSubMessage)
            .setContentText(reminderMessage)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setBubbleMetadata(bubbleMetadata)

        // Create notification
        val chatBot = Person.Builder()
            .setBot(true)
            .setName("BubbleBot")
            .setImportant(true)
            .build()


//        val notificationBuilder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
//            .setSmallIcon(R.mipmap.ic_launcher)
//            .setContentTitle("Reminder fired")
//            .setContentText(Date(System.currentTimeMillis()).toString())
//            .setAutoCancel(true)
//            .setLights(Color.GREEN, 3000, 3000)
//            .setContentIntent(pendingIntent)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, "Reminder Alarms", importance)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val random = Random()
        val id = random.nextInt(9999 - 1000) + 1000
        notificationManager.notify(id, notificationBuilder.build())
    }
}