package com.app.meister_search.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "taskarchive")
data class TaskArchive(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    val taskName: String,
    var taskId: String,
    val projectName: String
)