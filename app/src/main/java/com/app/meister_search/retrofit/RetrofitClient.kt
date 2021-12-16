package com.app.meister_search.retrofit

import android.os.SystemClock
import com.app.meister_search.BuildConfig
import com.app.meister_search.utils.Constants
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private val retrofitClient: Retrofit.Builder by lazy {

        val levelType: Level = if (BuildConfig.BUILD_TYPE.contentEquals("debug"))
            Level.BODY else Level.NONE

        val logging = HttpLoggingInterceptor()
        logging.level = levelType

        val okhttpClient = OkHttpClient.Builder()

        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 1

        val interceptor = Interceptor { chain ->
            SystemClock.sleep(400)
            chain.proceed(chain.request())
        }

        okhttpClient.addInterceptor(logging)
        okhttpClient.addNetworkInterceptor(interceptor)
        okhttpClient.dispatcher(dispatcher)

        Retrofit.Builder()
            .baseUrl(Constants.SERVER_URL)
            .client(okhttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: APIInterface by lazy {
        retrofitClient
            .build()
            .create(APIInterface::class.java)
    }
}