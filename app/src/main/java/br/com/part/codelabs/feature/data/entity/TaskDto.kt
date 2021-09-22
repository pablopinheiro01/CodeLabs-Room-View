package br.com.part.codelabs.feature.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime

@Entity(tableName = "task_table")
data class TaskDto(
    @PrimaryKey(autoGenerate = true) val id:Long = 0,
    var name:String,
    val state:String = Status.TODO.name,
    val date: OffsetDateTime? = null
)

enum class Status {
    TODO,
    PROGRESS,
    DONE,
    UNDEFINED;

    companion object{
        fun setValueOf(name: String): Status {
            return values()
                .find { it.name.equals(name, ignoreCase = true) } ?: UNDEFINED
        }
    }
}