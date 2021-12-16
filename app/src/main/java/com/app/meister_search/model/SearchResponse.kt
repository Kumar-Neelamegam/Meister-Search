package com.app.meister_search.model


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("paging")
    val paging: Paging? = null,
    @SerializedName("results")
    val results: Results? = null
)