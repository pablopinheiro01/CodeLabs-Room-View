package br.com.part.codelabs.add.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import br.com.part.codelabs.CdlApplication
import br.com.part.codelabs.R
import br.com.part.codelabs.add.TaskAddViewModel
import br.com.part.codelabs.feature.data.entity.TaskDto
import com.google.android.material.snackbar.Snackbar

class TaskAddActivity : AppCompatActivity() {

    private lateinit var viewModel: TaskAddViewModel

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
        val edtTaskDetailName = findViewById<TextView>(R.id.edtTaskDetailName)

        btnTaskNewSave.setOnClickListener{
            val name = edtTaskDetailName.text.toString()
            if(name.isNotEmpty()){
                viewModel.insert(TaskDto(name = name))
                finish()
            }else{
                Snackbar.make(edtTaskDetailName, "Name is required", Snackbar.LENGTH_LONG).show()
            }
        }

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
}