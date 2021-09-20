package br.com.part.codelabs.detail.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.part.codelabs.CdlApplication
import br.com.part.codelabs.R
import br.com.part.codelabs.detail.TaskDetailViewModel
import br.com.part.codelabs.feature.data.entity.Status.*
import br.com.part.codelabs.feature.data.entity.TaskDto
import com.google.android.material.snackbar.Snackbar

class TaskDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: TaskDetailViewModel
    private lateinit var selectedTask: TaskDto
    private lateinit var selectedStatus: String
    private lateinit var adapter: ArrayAdapter<String>



    private val status = arrayOf(
        TODO.name,
        PROGRESS.name,
        DONE.name
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        val edtTaskDetailName = findViewById<EditText>(R.id.edtTaskDetailName)
        val spnDetailStatus = findViewById<Spinner>(R.id.spinnerTaskDetailStatus)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        startStatus()

        viewModel = ViewModelProvider(
            this,
            TaskDetailViewModel.TaskDetailViewModelFactory(
                CdlApplication.instance
            )
        ).get(TaskDetailViewModel::class.java)

        viewModel.task.observe(this, Observer {
            selectedTask = it
            edtTaskDetailName.setText(it.name)

            status.forEachIndexed { index, item ->
                if(item == it.state){
                    selectedStatus = item
                    spnDetailStatus.setSelection(index)
                }
            }
        })

        val id = intent.getLongExtra(EXTRA_TASK_ID, 0 )
        viewModel.start(id)

        btnUpdate.setOnClickListener {
            val name = edtTaskDetailName.text.toString()
            if(name.isNotEmpty()){
                val updatedTask = selectedTask.copy(name = name, state = selectedStatus)
                viewModel.update(updatedTask)
                Snackbar.make(btnUpdate, "Task updated", Snackbar.LENGTH_LONG).show()
                finish()
            }else{
                Toast.makeText(this, "Name is required", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_task_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun startStatus(){
        val spinnerTask = findViewById<Spinner>(R.id.spinnerTaskDetailStatus)

        adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,status)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerTask.adapter = adapter

        spinnerTask.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedStatus = status[position]
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)

        when(item.itemId){
            android.R.id.home -> finish()
            R.id.action_delete -> {
                viewModel.task.removeObservers(this)
                viewModel.delete(selectedTask.id)
                Snackbar.make(btnUpdate,"Task deleted", Snackbar.LENGTH_LONG).show()
                finish()

            }
            else -> return super.onOptionsItemSelected(item)
        }
        return false
    }

    companion object{
        private const val EXTRA_TASK_ID = "EXTRA_TASK_DETAIL_ID"

        fun start(context: Context, taskId:Long): Intent{
            return Intent(context, TaskDetailActivity::class.java)
                .apply {
                    putExtra(EXTRA_TASK_ID, taskId)
                }
        }
    }
}