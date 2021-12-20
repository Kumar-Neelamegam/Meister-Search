package com.app.meister_search.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.meister_search.model.CustomTask
import com.app.meister_search.model.Project
import com.app.meister_search.model.SearchResponse
import com.app.meister_search.model.Section
import com.app.meister_search.persistence.AppDatabase
import com.app.meister_search.persistence.TaskArchive
import com.app.meister_search.persistence.TaskArchiveDao
import com.app.meister_search.repository.SearchActivityRepository
import org.json.JSONObject

class SearchActivityViewModel(app: Application) : AndroidViewModel(app) {
    var customTaskList: MutableLiveData<List<CustomTask>> = MutableLiveData()
    private val appContext: Application = app

    fun getSearchResults(term: Any?): MutableLiveData<List<CustomTask>>? {

        if (checkInternetConnection()) { //With internet
            val jsonObject = JSONObject()
            jsonObject.put("text", term)
            val searchLiveData: MutableLiveData<SearchResponse> =
                SearchActivityRepository.getSearchApiCall(jsonObject, "object")
            if (searchLiveData.value != null) {
                val results = searchLiveData.value?.results
                val paging = searchLiveData.value?.paging
                val list: MutableList<CustomTask> = arrayListOf()
                if (paging!!.totalResults!! > 0) {
                    results?.tasks?.forEach { task ->
                        val selectedSection: Section =
                            results.sections?.filter { s -> s.id == task.sectionId }!!.single()
                        val selectedProject: Project =
                            results.projects?.filter { p -> p.id == selectedSection.projectId }!!
                                .single()
                        var taskStatus: Boolean? = null
                        if (task.status == 1) {
                            taskStatus = true
                        } else if (task.status == 18) {
                            taskStatus = false
                        }
                        val customTaskItem =
                            CustomTask(task.id, task.name!!, selectedProject.name!!, taskStatus!!)
                        list.add(customTaskItem)
                        insertSampleData(task.id, task.name, selectedProject.name, taskStatus)
                    }
                }
                customTaskList.postValue(list)
                return customTaskList
            }
        } else { //Without internet
            readFromCache(term)
        }
        return null
    }

    private fun readFromCache(term: Any?) {
        val db = AppDatabase.getDatabase(appContext)
        val list: MutableList<CustomTask> = arrayListOf()

        val task: TaskArchiveDao = db!!.taskArchiveDao()
        var data: LiveData<List<TaskArchive>> = task.getAllRelevantTasks(term.toString())
        data.value?.forEach { taskArchive ->
            val customTaskItem = CustomTask(
                taskArchive.id,
                taskArchive.taskName,
                taskArchive.projectName,
                taskArchive.status
            )
            list.add(customTaskItem)
        }
        customTaskList.postValue(list)
    }

    fun checkInternetConnection(): Boolean {
        val cm = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    fun insertSampleData(id: Int?, taskName: String?, projectName: String?, taskStatus: Boolean?) {
        val db = AppDatabase.getDatabase(appContext)
        val task: TaskArchiveDao = db!!.taskArchiveDao()
        task.insertTaskArchive(TaskArchive(null, taskName!!, id!!, projectName!!, taskStatus!!))
    }
}

