package br.com.part.codelabs.add

import android.app.Application
import androidx.lifecycle.*
import br.com.part.codelabs.CdlApplication
import br.com.part.codelabs.base.AppDataBase
import br.com.part.codelabs.feature.data.TaskRepository
import br.com.part.codelabs.feature.data.entity.TaskDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class TaskAddViewModel(application: Application ) : AndroidViewModel(application) {

    private val repository :TaskRepository
    val taskId = MutableLiveData<Long>()

    init {
        val dao = AppDataBase.getDataBase(application).taskDao()
        repository = TaskRepository.create(dao)
    }

    fun insert(task: TaskDto){
        viewModelScope.launch(Dispatchers.IO) {
            taskId.postValue(
                repository.addTask(task)
            )
        }
    }

    class TaskViewModelFactory constructor(private val application: Application):
        ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if(modelClass.isAssignableFrom(TaskAddViewModel::class.java)){
                TaskAddViewModel(
                    this.application
                ) as T
            }else{
                throw IllegalArgumentException("viewModel Not Found")
            }
        }
    }

}