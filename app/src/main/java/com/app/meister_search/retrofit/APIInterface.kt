package com.app.meister_search.retrofit

import com.app.meister_search.model.SearchResponse
import com.app.meister_search.utils.Constants.BEARER_TOKEN
import com.app.meister_search.utils.Constants.RESPONSE_TYPE
import retrofit2.Call
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface APIInterface {

    @Headers(
        "Accept: $RESPONSE_TYPE",
        "Content-Type: $RESPONSE_TYPE",
        "Authorization: Bearer $BEARER_TOKEN"
    )
    @POST("/search")
    fun search(
        @Query(value = "filter", encoded = true) filter: Any?,
        @Query("response_format") response_format: String?
    ): Call<SearchResponse?>?
}