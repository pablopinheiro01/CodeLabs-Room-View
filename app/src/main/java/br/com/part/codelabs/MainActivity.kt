package br.com.part.codelabs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import br.com.part.codelabs.feature.data.entity.TaskDto
import br.com.part.codelabs.feature.presentation.TaskListViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TaskListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this,
            TaskListViewModel.TaskViewModelFactory(CdlApplication.instance)
        ).get(TaskListViewModel::class.java)

        viewModel.alltasks.observe(this, {
            Log.d("MyTask", it.toString())
            Toast.makeText(this, it.size.toString(), Toast.LENGTH_LONG).show()
        })

        viewModel.addTask(TaskDto(name = "test"))
    }
}