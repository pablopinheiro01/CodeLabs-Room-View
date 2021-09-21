package br.com.part.codelabs.feature

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.text.format.DateFormat
import android.widget.TimePicker
import java.util.*

class TimePickerFragment: DialogFragment() {

    lateinit var timeListener: TimePickerDialog.OnTimeSetListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //Usa a hora do tempo corrente para montar o picker
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]


        //Criar uma nova instancia TimePickerDialog
        return TimePickerDialog(
            activity, timeListener, hour, minute,
            DateFormat.is24HourFormat(activity)
        )
    }

    fun setTimeSetListener(listener: TimePickerDialog.OnTimeSetListener){
        timeListener = listener
    }

    companion object{
        fun newInstance(listener: TimePickerDialog.OnTimeSetListener): TimePickerFragment {
            val instance = TimePickerFragment()
            instance.setTimeSetListener(listener)
            return instance
        }
    }
}