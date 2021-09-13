package br.com.part.codelabs.base

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import br.com.part.codelabs.feature.data.entity.TaskDto
import br.com.part.codelabs.feature.data.local.TaskDao

@Database(entities = [TaskDto::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile //evita que a base de dados seja alterada em diferentes threads
        private var instance: AppDataBase? = null

        fun getDataBase(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                instance ?: buildDataBase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDataBase(context: Context): AppDataBase{
            return Room.databaseBuilder(context, AppDataBase::class.java, "task_table")
                .fallbackToDestructiveMigration() //caso precisamos migrar o app não ocorrera um problema de crash.
                .build()
        }
    }


}