package com.guesswho.items.adapter

import com.guesswho.items.model.Car
import com.guesswho.items.model.Event

/**
 *
 * @author Maxim on 27.02.20
 */
interface IOnOptionClicked {
    fun onClick(event: Event, car: Car)
}