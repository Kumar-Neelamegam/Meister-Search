package com.app.meister_search.persistence

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskArchiveDao {
    @Insert
    fun insertTaskArchive(taskArchive: TaskArchive)

    @Insert
    fun insertAllTaskArchive(taskArchive: List<TaskArchive>)

    @Query("Select * from taskarchive")
    fun getAllTaskArchive(): LiveData<List<TaskArchive>>

    @Query("Select * from taskarchive where id=:Id")
    fun getTaskArchive(Id: Int): TaskArchive

    @Query("Select * from taskarchive where taskName like :searchTerm")
    fun getAllRelevantTasks(searchTerm: String): LiveData<List<TaskArchive>>

    @Update
    fun updateArchive(taskArchive: TaskArchive)

    @Delete
    fun deleteArchive(taskArchive: TaskArchive)

}