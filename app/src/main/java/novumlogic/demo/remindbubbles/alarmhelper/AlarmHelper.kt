package novumlogic.demo.remindbubbles.alarmhelper

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import java.util.*

/**
 * Created by Priya Sindkar.
 */
class AlarmHelper {

    fun setTimerForSpecificTime(
        context: Context,
        reminderDate: String,
        reminderTime: String,
        reminderMessage: String,
        reminderSubMessage : String
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val myIntent = Intent(context, AlarmBroadcastReceiver::class.java)
        myIntent.putExtra("REMINDER_MESSAGE", reminderMessage)
        myIntent.putExtra("REMINDER_SUB_MESSAGE", reminderSubMessage)
        val random = Random()
        val id = random.nextInt(9999 - 1000) + 1000
        val pendingIntent = PendingIntent.getBroadcast(context, id, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val firingCal = Calendar.getInstance()
        val currentCal = Calendar.getInstance()

        firingCal.set(Calendar.DAY_OF_MONTH, reminderDate.split("-")[1].toInt())
        firingCal.set(Calendar.MONTH, reminderDate.split("-")[0].toInt())
        firingCal.set(Calendar.YEAR, reminderDate.split("-")[2].toInt())

        firingCal.set(Calendar.HOUR_OF_DAY, reminderTime.split(":")[0].toInt())
        firingCal.set(Calendar.MINUTE, reminderTime.split(":")[1].toInt())
        firingCal.set(Calendar.SECOND, 0)
        firingCal.set(Calendar.MILLISECOND, 0)

        var intendedTime = firingCal.timeInMillis
        val currentTime = currentCal.timeInMillis

        if (intendedTime < currentTime) {
            firingCal.add(Calendar.DAY_OF_MONTH, 1)
            intendedTime = firingCal.timeInMillis
        }

        Log.d("NOTIFICATION", "intendedTime : ${Date(intendedTime).toString()}")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, intendedTime, pendingIntent)
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, intendedTime, pendingIntent)
        }
    }

    fun cancelAlarm(context: Context, alarmId: Int) {
        if (alarmId != -1) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val myIntent = Intent(context, AlarmBroadcastReceiver::class.java)
            val pendingIntent =
                PendingIntent.getBroadcast(context, alarmId, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager.cancel(pendingIntent)
        }
    }
}