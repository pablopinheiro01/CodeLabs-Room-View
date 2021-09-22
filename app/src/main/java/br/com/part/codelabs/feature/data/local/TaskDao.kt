package br.com.part.codelabs.feature.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.part.codelabs.feature.data.entity.TaskDto

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table ORDER BY name ASC")
    fun getAllTasks(): LiveData<List<TaskDto>>

    @Query("SELECT * FROM task_table WHERE id = :id")
    fun getTaskById(id: Long): LiveData<TaskDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(taskDto: TaskDto): Long

    @Query("DELETE FROM task_table")
    fun deletaAll()

    @Query("DELETE FROM task_table WHERE id = :id")
    fun deleteById(id:Long)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(taskDto: TaskDto)

    @Query("SELECT * FROM task_table ORDER BY name DESC")
    fun getAllTasksOderByDesc(): LiveData<List<TaskDto>>

    @Query("SELECT * FROM task_table ORDER BY date ASC")
    fun getAllTasksOrderByDate(): LiveData<List<TaskDto>>

}