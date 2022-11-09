
    package com.example.apptaglist

    import androidx.room.Entity
    import androidx.room.PrimaryKey

    @Entity(tableName = "task_entity")
    data class TasksEntity (
        @PrimaryKey(autoGenerate = true)
        var id:Int = 0,
        var name:String = "",
        var isDone:Boolean = false
    )

