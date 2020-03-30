package com.guesswho.items.repository

import okhttp3.OkHttpClient
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.robolectric.annotation.Config
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

/**
 *
 * @author Maxim on 28.02.20
 */

@Config(shadows = [NetworkSecurityPolicyShadow::class])
class RetrofitClientTest {
    private var mockWebServer = MockWebServer()

    private lateinit var apiService: Api
    private val baseUrl = "http://10.0.2.2:8080/"

    @Before
    fun setup() {
        mockWebServer.start()

        val okHttpClient = OkHttpClient().newBuilder().apply {
            readTimeout(60, TimeUnit.SECONDS)
            //addInterceptor(retryInterceptor)
        }.build()

        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url(baseUrl))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(Api::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testApi() {
        val dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/cars" -> {
                        val response = MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_OK)
                            .setBody("")
                        response
                    }
                    else -> MockResponse().setResponseCode(404)
                }
            }
        }
        mockWebServer.dispatcher = dispatcher
    }
}