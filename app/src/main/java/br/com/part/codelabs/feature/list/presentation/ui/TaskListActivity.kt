package br.com.part.codelabs.feature.list.presentation.ui

import android.app.ActivityManager
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import br.com.part.codelabs.CdlApplication
import br.com.part.codelabs.R
import br.com.part.codelabs.add.ui.TaskAddActivity
import br.com.part.codelabs.detail.ui.TaskDetailActivity
import br.com.part.codelabs.feature.data.entity.TaskDto
import br.com.part.codelabs.feature.presentation.TaskListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class TaskListActivity : AppCompatActivity() {

    private lateinit var viewModel: TaskListViewModel
    private lateinit var adapter: TasklistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        adapter = TasklistAdapter(this, ::taskListClickListener)

        val rvTaskList = findViewById<RecyclerView>(R.id.rvTaskList)
        rvTaskList.adapter = adapter

        viewModel = ViewModelProvider(
            this,
            TaskListViewModel.TaskViewModelFactory(CdlApplication.instance)
        ).get(TaskListViewModel::class.java)

        setObserver()

        val fabAddTask = findViewById<FloatingActionButton>(R.id.fabAddTask)

        fabAddTask.setOnClickListener{
            Log.i("TaskListActivity", "Clicou no FAB")
            val intent =
                TaskAddActivity.start( this )
            startActivity(intent)
        }
        Log.i("TaskListActivity", "Criando uma nova task")
        viewModel.addTask(TaskDto(name = "Testando"))

    }

    private fun taskListClickListener(taskId: Long){
        startActivity(TaskDetailActivity.start(this, taskId))
        Log.i("TaskListActivity","OnClickListener chamado com o id ${taskId}")
    }

    private fun setObserver(){
        viewModel.alltasks.observe(this, Observer {
            adapter.submit(it)
        })
    }

}
