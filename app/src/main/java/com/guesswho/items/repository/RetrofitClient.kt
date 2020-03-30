package com.guesswho.items.repository

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit



object RetrofitClient {

    private val retrofit: Retrofit
    private const val baseUrl = "http://10.0.2.2:8080/"


    val api: Api
        get() = retrofit.create(Api::class.java)

    init {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.HEADERS
            level = HttpLoggingInterceptor.Level.BODY
        }

        /*
        val retryInterceptor = Interceptor {
            val request = it.request()
            var response = it.proceed(request)

            var count = 0
            while (!response.isSuccessful && count < 3) {
                count += 1
                response.close()
                response = it.proceed(request)
            }
            response
        }*/

        val okHttpClient = OkHttpClient().newBuilder().apply {
            addInterceptor(loggingInterceptor)
            readTimeout(60, TimeUnit.SECONDS)
            //addInterceptor(retryInterceptor)
        }.build()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}

