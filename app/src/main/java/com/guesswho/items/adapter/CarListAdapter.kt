package com.guesswho.items.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.guesswho.items.R
import com.guesswho.items.model.Car
import com.guesswho.items.model.Event
import com.guesswho.items.utils.CarsDiffUtilCallback
import kotlinx.android.synthetic.main.car_item.view.*
import java.util.*


/**
 *
 * @author Maxim on 2020-01-29
 */
class CarListAdapter(private val cars: ArrayList<Car>, private val adapterOnClick: IOnOptionClicked) :
    RecyclerView.Adapter<CarListAdapter.RateViewHolder>() {

    fun updateList(newCars: List<Car>) {
        val diffUtilRes =
            DiffUtil.calculateDiff(CarsDiffUtilCallback(cars, newCars))
        cars.clear()
        cars.addAll(newCars)
        diffUtilRes.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = RateViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
    )

    override fun getItemCount() = cars.size
    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        val currentCar = cars[position]

        holder.itemView.apply {
            iv_delete.setOnClickListener {
                adapterOnClick.onClick(Event.DELETE, currentCar)
            }

            iv_edit.setOnClickListener {
                adapterOnClick.onClick(Event.EDIT, currentCar)
            }

            tv_name.text = currentCar.brand
            tv_horsepower.text = currentCar.horsePower.toString()

            front_layout.setOnClickListener {

            }
        }
    }


    data class RateViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}