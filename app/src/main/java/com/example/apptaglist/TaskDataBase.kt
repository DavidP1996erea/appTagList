package com.example.apptaglist

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [TasksEntity::class], version = 1)

abstract class TasksDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao



}
