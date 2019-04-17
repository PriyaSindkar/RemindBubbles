package novumlogic.demo.remindbubbles

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_reminder.*
import novumlogic.demo.remindbubbles.alarmhelper.AlarmHelper
import java.util.*


/**
 * Created by Priya Sindkar.
 */
class AddReminderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reminder)
        etTime.setOnClickListener {
            setTimeField()
        }

        etDate.setOnClickListener {
            setDateField()
        }

        btnSubmit.setOnClickListener {

            if (etDate.text.isNullOrEmpty() || etTime.text.isNullOrEmpty()) {
                return@setOnClickListener
            }
            val alarmHelper = AlarmHelper()
            val reminderSubMessage = when {
                radioCall.isChecked -> "Reminder to Call"
                radioEmail.isChecked -> "Reminder to Send Email"
                else -> ""
            }
            alarmHelper.setTimerForSpecificTime(this, etDate.text.toString(), etTime.text.toString(),
                etReminderMessage.text.toString(), reminderSubMessage)
        }
    }

    private fun setTimeField() {
        val mCurrentTime = Calendar.getInstance()
        val hour = mCurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mCurrentTime.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this@AddReminderActivity,
            TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                val selectedTime = "${String.format("%02d", selectedHour)}:${String.format("%02d", selectedMinute)}"
                etTime.setText(selectedTime)
            }, hour, minute, false
        )
        timePickerDialog.show()
    }

    private fun setDateField() {
        val myCalendar = Calendar.getInstance()
        DatePickerDialog(
            this@AddReminderActivity, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val selectedDate = "${String.format("%02d", monthOfYear)}-${String.format("%02d", dayOfMonth)}-$year"
                etDate.setText(selectedDate)
            }, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }
}