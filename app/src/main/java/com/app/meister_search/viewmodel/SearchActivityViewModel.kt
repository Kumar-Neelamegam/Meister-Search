package com.app.meister_search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.meister_search.model.SearchResponse
import com.app.meister_search.repository.SearchActivityRepository

class SearchActivityViewModel : ViewModel() {
    var searchLiveData: MutableLiveData<SearchResponse>? = null
    fun getSearchResults(term: Any?, respFormat: String): LiveData<SearchResponse>? {
        searchLiveData = SearchActivityRepository.getSearchApiCall(term, respFormat)
        //check internet or get the data from the local db
        return searchLiveData
    }
}