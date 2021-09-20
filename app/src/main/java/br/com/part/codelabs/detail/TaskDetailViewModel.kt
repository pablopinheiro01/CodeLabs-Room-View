package br.com.part.codelabs.detail

import android.app.Application
import androidx.lifecycle.*
import br.com.part.codelabs.base.AppDataBase
import br.com.part.codelabs.feature.data.TaskRepository
import br.com.part.codelabs.feature.data.entity.TaskDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class TaskDetailViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var repository: TaskRepository

    private val _id = MutableLiveData<Long>()
    private var _task: LiveData<TaskDto> = _id
        .switchMap {
            id ->
            repository.getTaskById(id)
        }

    val task: LiveData<TaskDto> = _task

    init {
        val dao = AppDataBase.getDataBase(application).taskDao()
        repository = TaskRepository.create(dao)
    }

    fun update(task: TaskDto){
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(task)
        }
    }

    fun delete(taskId:Long){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteById(taskId)
        }
    }

    fun start(id:Long){
        _id.value = id
    }

    class TaskDetailViewModelFactory constructor(private val application: Application):
            ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(TaskDetailViewModel::class.java)){
                TaskDetailViewModel(
                    this.application
                ) as T
            } else {
                throw IllegalArgumentException("viewModel not found")
            }
        }
    }

}