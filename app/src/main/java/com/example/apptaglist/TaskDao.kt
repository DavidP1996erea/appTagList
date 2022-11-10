package com.example.apptaglist

import androidx.room.*


@Dao
interface TaskDao {

    @Query("SELECT * FROM task_entity")
  suspend fun getAllTasks(): MutableList <TasksEntity>

    @Query ("SELECT * FROM task_entity WHERE id=:id")
    suspend  fun getTaskById(id: Long): TasksEntity


    @Insert
    suspend fun addTask(taskEntity : TasksEntity):Long


    @Update
    suspend fun updateTask(task: TasksEntity)


    @Delete
    suspend fun deleteTask(task: TasksEntity)

}