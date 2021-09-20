package br.com.part.codelabs

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.part.codelabs.base.AppDataBase
import br.com.part.codelabs.feature.data.entity.TaskDto
import br.com.part.codelabs.feature.data.local.TaskDao
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import kotlin.math.sign

@RunWith(AndroidJUnit4::class)
class TaskDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var taskDao: TaskDao
    private lateinit var db: AppDataBase
    private val task = TaskDto(name = "Test")

    @Before
    fun createDb(){
        val context: Context = ApplicationProvider.getApplicationContext()

        //Usamos uma base de dados inmemory porque a informação armazenada pode desparecer quando o processo for exterminado
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java)
        //Habilito as queries na main thread, para realização dos testes
            .allowMainThreadQueries()
            .build()

        taskDao = db.taskDao()

    }

    @After
    fun closeDb(){
        db.close()
    }

    @Test
    fun test(){
        print("Testando ....")
    }

    @Test
    fun getAllTasks(){
        //Given - Nao e aplicavel nesse teste

        //When
        val allTasks = taskDao.getAllTasks().waitForValue()
        //Then
        assertEquals(allTasks.size, 0)
    }

    @Test
    fun insertAndGetAllTasks(){
        //given

        //when
        taskDao.insert(task)

        //Then
        var allTasks = taskDao.getAllTasks().waitForValue()

        assertEquals(allTasks[0].name, task.name)

    }

    @Test
    fun deleteAllTasks(){
        //given
//        val task = TaskDto(name = "TAREFA DE COMPRAS")
        taskDao.insert(task)

        //when
        taskDao.deletaAll()

        val allTasks = taskDao.getAllTasks().waitForValue().size

        //then
        assertEquals(allTasks, 0)

    }

    @Test
    fun getTaskById(){
        //given
        taskDao.insert(task)
        val allTasks = taskDao.getAllTasks().waitForValue()
        val clicked = allTasks[0]

        //when
        val taskById = taskDao.getTaskById(clicked.id).waitForValue()

        //then
        assertEquals(taskById.name, clicked.name)

    }

    @Test
    fun deleteById(){
        //Given
        taskDao.insert(task)
        val allTasks = taskDao.getAllTasks().waitForValue()

        //When
        taskDao.deleteById(task.id)

        //Then
        assertNotEquals(task.id,allTasks[0])
    }

    @Test
    fun update(){
        //Given
        taskDao.insert(task)
        val allTasks = taskDao.getAllTasks().waitForValue()
        val updateTask = allTasks[0].copy(name = "ALTERADO")
        //When
        taskDao.update(updateTask)
        //Then
        val refreshTasks = taskDao.getAllTasks().waitForValue()
        assertEquals(refreshTasks[0].name, updateTask.name)
    }


}