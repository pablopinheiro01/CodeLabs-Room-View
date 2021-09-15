package br.com.part.codelabs.feature.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.part.codelabs.feature.data.entity.TaskDto

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table ORDER BY name ASC")
    fun getAllTasks(): LiveData<List<TaskDto>>

    @Query("SELECT * FROM task_table WHERE id = :id")
    fun getTaskById(id: Int): LiveData<TaskDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(taskDto: TaskDto)

    @Query("DELETE FROM task_table")
    fun deletaAll()

    @Query("DELETE FROM task_table WHERE id = :id")
    fun deleteById(id:Int)

}