package com.guesswho.items.model

import kotlin.random.Random

/**
 *
 * @author Maxim on 27.02.20
 */
data class Car(val id: Int = Random.nextInt(0, Int.MAX_VALUE), val brand: String, val horsePower: Int)