package br.com.part.codelabs.add.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import br.com.part.codelabs.CdlApplication
import br.com.part.codelabs.R
import br.com.part.codelabs.add.TaskAddViewModel
import br.com.part.codelabs.feature.DatePickerFragment
import br.com.part.codelabs.feature.TimePickerFragment
import br.com.part.codelabs.feature.data.entity.TaskDateDto
import br.com.part.codelabs.feature.data.entity.TaskDto
import com.google.android.material.snackbar.Snackbar
import org.threeten.bp.OffsetDateTime

class TaskAddActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener  {

    private lateinit var viewModel: TaskAddViewModel
    private var taskDate: TaskDateDto = TaskDateDto()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_add)
        //adiciona a seta do botao voltar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(
            this,
            TaskAddViewModel.TaskViewModelFactory(
                CdlApplication.instance
            )
        ).get(TaskAddViewModel::class.java)

        val btnTaskNewSave = findViewById<Button>(R.id.btnTaskNewSave)
        val edtTaskDetailName = findViewById<TextView>(R.id.edtTaskNameNew)
        val txtTaskNewDate = findViewById<TextView>(R.id.txtTaskNewDate)
        val txtTaskNewTime = findViewById<TextView>(R.id.txtTaskNewTime)

        btnTaskNewSave.setOnClickListener{
            val name = edtTaskDetailName.text.toString()
            if(name.isNotEmpty()){
                var offsetDateTime: OffsetDateTime? = null
                if(taskDate.isDateReady() && taskDate.isTimeReady()){
                    offsetDateTime = setTaskDateTime(taskDate)
                }else{
                    Toast.makeText(this, "Create Task without date", Toast.LENGTH_LONG).show()
                }
                viewModel.insert(TaskDto(name = name, date = offsetDateTime))
                finish()
            }else{
                Snackbar.make(edtTaskDetailName, "Name is required", Snackbar.LENGTH_LONG).show()
            }
        }

        txtTaskNewDate.setOnClickListener{
            showDatePickerDialog()
        }

        txtTaskNewTime.setOnClickListener {
            showTimePickerDialog()
        }
    }

    private fun showDatePickerDialog(){
        val newFragment: DialogFragment = DatePickerFragment.newInstance(this)
        newFragment.show(supportFragmentManager, "timePicker")
    }

    private fun showTimePickerDialog(){
        val newFragment: DialogFragment = TimePickerFragment.newInstance(this)
        newFragment.show(supportFragmentManager, "timePicker")
    }

    private fun setTaskDateTime(taskDateDto: TaskDateDto): OffsetDateTime{
        return OffsetDateTime.of(
            taskDateDto.year,
            taskDateDto.month,
            taskDateDto.day,
            taskDateDto.hour,
            taskDateDto.minute,
            0,0,OffsetDateTime.now().offset
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            else -> return super.onOptionsItemSelected(item)
        }
        return false
    }

    companion object{
        fun start(context:Context): Intent {
            return Intent(context, TaskAddActivity::class.java)
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        taskDate = taskDate.copy(day = dayOfMonth, month = month +1 , year = year )
        findViewById<TextView>(R.id.txtTaskNewDate).text = "$dayOfMonth/${this.taskDate.month}/$year"
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        taskDate = taskDate.copy(hour = hourOfDay, minute = minute)
        findViewById<TextView>(R.id.txtTaskNewTime).text = "$hourOfDay:$minute"
    }
}