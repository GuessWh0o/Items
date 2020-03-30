package com.guesswho.items.screens.first_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.guesswho.items.model.Car
import com.guesswho.items.model.ViewState
import com.guesswho.items.repository.ItemsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 *
 * @author Maxim on 27.02.20
 */

class FirstViewModel : ViewModel() {

    val data: MutableLiveData<MutableList<Car>> = MutableLiveData()

    val states: MutableLiveData<ViewState> = MutableLiveData(ViewState.LOADING)

    private val repo : ItemsRepository = ItemsRepository()

    private val coroutineContext: CoroutineContext get() = Job() + Dispatchers.Default
    private val scope = CoroutineScope(coroutineContext)

    fun deleteCar(car: Car) {
        scope.launch {
            val response = repo.deleteCarById(car.id)
            if (response.code() in 200..299) {
                data.value?.remove(car)
                data.postValue(data.value)
                if(data.value.isNullOrEmpty()) { states.postValue(ViewState.EMPTY) }
            }
        }
    }

    fun addCar(car: Car) {
        scope.launch {
            val response = repo.addCar(car)
            if (response in 200..299) {
                data.value?.add(car)
                data.postValue(data.value)
                states.postValue(ViewState.LOADED)
            }
        }
    }

    fun deleteAll() {
        scope.launch {
            val response = repo.deleteAll()
            if (response in 200..299) {
                data.value?.clear()
                data.postValue(data.value)
                states.postValue(ViewState.EMPTY)
            }
        }
    }

    fun fetch() {
        states.value = ViewState.LOADING
        scope.launch {
            val list = repo.getList()
            data.postValue(list)
            states.postValue(if(list.isNullOrEmpty()) {ViewState.EMPTY} else ViewState.LOADED)
        }
    }
}