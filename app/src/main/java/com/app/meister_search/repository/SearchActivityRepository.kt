package com.app.meister_search.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.meister_search.model.SearchResponse
import com.app.meister_search.persistence.TaskArchive
import com.app.meister_search.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object SearchActivityRepository {

    val searchResponse = MutableLiveData<SearchResponse>()

    fun getSearchApiCall(term: Any?, respFormat: String): MutableLiveData<SearchResponse> {
        RetrofitClient.apiInterface.search(term, respFormat)
            ?.enqueue(object : Callback<SearchResponse?> {
                override fun onFailure(call: Call<SearchResponse?>, t: Throwable) {
                    Log.v("DEBUG : ", t.message.toString())
                }

                override fun onResponse(
                    call: Call<SearchResponse?>,
                    response: Response<SearchResponse?>
                ) {
                    Log.v("DEBUG : ", response.body().toString())
                    searchResponse.value = response.body()
                }
            })
        return searchResponse
    }

    fun search(name: String): LiveData<List<TaskArchive>>? {
        return null
    }


}

