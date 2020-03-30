package com.guesswho.items.repository

import com.google.gson.JsonObject
import com.guesswho.items.model.Car
import retrofit2.Response
import retrofit2.http.*

interface Api {
    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("/cars")
    suspend fun getList(
    ):  List<Car>

    @HTTP(method = "DELETE", path = "/cars/{id}", hasBody = false)
    suspend fun deleteCarById(@Path("id") id: Int): Response<String>

    @HTTP(method = "DELETE", path = "/cars", hasBody = false)
    suspend fun deleteAll(): Response<String>

    @POST("/cars")
    suspend fun addCar(@Body jsonObject: JsonObject): Response<String>
}