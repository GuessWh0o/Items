package com.guesswho.items.repository

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.guesswho.items.model.Car
import org.koin.dsl.module
import retrofit2.Response

/**
 *
 * @author Maxim on 27.02.20
 */

val repoModule = module {
    single { ItemsRepository() }
}

class ItemsRepository {

    suspend fun getList(): MutableList<Car> {
        return RetrofitClient.api.getList().toMutableList()
    }

    suspend fun deleteCarById(id: Int): Response<String> = RetrofitClient.api.deleteCarById(id)

    suspend fun deleteAll(): Int = RetrofitClient.api.deleteAll().code()

    suspend fun addCar(car: Car): Int {
        val gson = Gson()
        val jsonCar = gson.fromJson(gson.toJson(car), JsonObject::class.java)
        return RetrofitClient.api.addCar(jsonCar).code()
    }
}