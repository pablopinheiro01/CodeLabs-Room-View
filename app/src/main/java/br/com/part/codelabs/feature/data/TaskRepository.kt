package br.com.part.codelabs.feature.data

import androidx.lifecycle.LiveData
import br.com.part.codelabs.feature.data.entity.TaskDto
import br.com.part.codelabs.feature.data.local.TaskDao

class TaskRepository private constructor(
    private val localDataSource: TaskDao
) {
    suspend fun addTask(taskDto: TaskDto){
        localDataSource.insert(taskDto)
    }

    fun getAllTasks(): LiveData<List<TaskDto>> = localDataSource.getAllTasks()

    fun update(taskDto: TaskDto): Unit = localDataSource.update(taskDto)

    fun getTaskById(id:Long): LiveData<TaskDto> = localDataSource.getTaskById(id)

    fun deleteById(id:Long) = localDataSource.deleteById(id)

    companion object{
        fun create(localDataSource: TaskDao):TaskRepository{
            return TaskRepository(localDataSource)
        }
    }

}