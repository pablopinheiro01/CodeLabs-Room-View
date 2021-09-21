package br.com.part.codelabs.feature.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import br.com.part.codelabs.base.AppDataBase
import br.com.part.codelabs.feature.data.TaskRepository
import br.com.part.codelabs.feature.data.entity.TaskDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import java.util.*
import java.util.logging.Filter

class TaskListViewModel (application: Application): AndroidViewModel(application) {

    private lateinit var repository: TaskRepository
    private val _filter = MutableLiveData<FilterIntent>()

    private var _alltasks: LiveData<List<TaskDto>> = _filter
        .switchMap {
            when(it){
                FilterIntent.DATE -> repository.getAllTasksOrderByDate()
                FilterIntent.DESC -> repository.getAllTasksOrderByDesc()
                FilterIntent.ASC -> repository.getAllTasks()
                else -> {
                    throw IllegalArgumentException("filtro desconhecido")
                }
            }
        }

    val alltasks: LiveData<List<TaskDto>> = _alltasks

    init {
        val dao = AppDataBase.getDataBase(application).taskDao()
        repository = TaskRepository.create(dao)
        filter(FilterIntent.ASC)
    }

    fun addTask(taskDto: TaskDto){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(taskDto)
        }
    }

    fun deleteAll(){
        viewModelScope.launch( Dispatchers.IO ){
            repository.deleteAll()
        }
    }

    fun tasksOrderByDesc() {
        viewModelScope.launch(Dispatchers.IO ){

            _alltasks =  repository.getAllTasksOrderByDesc()
            Log.i("value_all","${_alltasks.value}")
        }
    }

    fun filter(intent: FilterIntent){
        _filter.value = intent
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


enum class FilterIntent{
    DESC,
    DATE,
    ASC
}