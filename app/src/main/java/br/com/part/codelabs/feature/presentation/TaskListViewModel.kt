package br.com.part.codelabs.feature.presentation

import android.app.Application
import androidx.lifecycle.*
import br.com.part.codelabs.CdlApplication
import br.com.part.codelabs.base.AppDataBase
import br.com.part.codelabs.feature.data.TaskRepository
import br.com.part.codelabs.feature.data.entity.TaskDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class TaskListViewModel (application: Application): AndroidViewModel(application) {

    private val repository: TaskRepository
    val alltasks: LiveData<List<TaskDto>>

    init {
        val dao = AppDataBase.getDataBase(application).taskDao()
        repository = TaskRepository.create(dao)
        alltasks = repository.getAllTasks()
    }

    fun addTask(taskDto: TaskDto){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(taskDto)
        }
    }

    class TaskViewModelFactory constructor(private val application: Application) :
            ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if(modelClass.isAssignableFrom(TaskListViewModel::class.java)) {
                TaskListViewModel(this.application) as T
            } else {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }

}