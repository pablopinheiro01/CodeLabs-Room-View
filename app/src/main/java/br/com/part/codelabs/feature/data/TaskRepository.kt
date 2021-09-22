package br.com.part.codelabs.feature.data

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import br.com.part.codelabs.feature.data.entity.TaskDto
import br.com.part.codelabs.feature.data.local.TaskDao

class TaskRepository private constructor(
    private val localDataSource: TaskDao
) {
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addTask(taskDto: TaskDto):Long {
        return localDataSource.insert(taskDto)
    }

    fun getAllTasks(): LiveData<List<TaskDto>> = localDataSource.getAllTasks()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    fun update(taskDto: TaskDto): Unit = localDataSource.update(taskDto)

    fun getTaskById(id:Long): LiveData<TaskDto> = localDataSource.getTaskById(id)

    fun deleteById(id:Long) = localDataSource.deleteById(id)

    fun deleteAll() = localDataSource.deletaAll()

    fun getAllTasksOrderByDesc():LiveData<List<TaskDto>> = localDataSource.getAllTasksOderByDesc()

    fun getAllTasksOrderByDate():LiveData<List<TaskDto>> = localDataSource.getAllTasksOrderByDate()

    companion object{
        fun create(localDataSource: TaskDao):TaskRepository{
            return TaskRepository(localDataSource)
        }
    }

}