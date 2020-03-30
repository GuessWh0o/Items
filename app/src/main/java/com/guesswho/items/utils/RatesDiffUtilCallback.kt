package com.guesswho.items.utils

import androidx.recyclerview.widget.DiffUtil
import com.guesswho.items.model.Car

/**
 *
 * @author Maxim on 2020-01-30
 */
class CarsDiffUtilCallback(
    private val oldList: List<Car>,
    private val newList: List<Car>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldPos: Int, newPos: Int) =
        oldList[oldPos].id == newList[newPos].id

    override fun areContentsTheSame(oldPos: Int, newPos: Int) =
        oldList[oldPos] == newList[newPos]
}