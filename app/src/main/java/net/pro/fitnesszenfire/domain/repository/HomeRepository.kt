package net.pro.fitnesszenfire.domain.repository

import net.pro.fitnesszenfire.data.repository.Results
import net.pro.fitnesszenfire.domain.model.Advertisement
import net.pro.fitnesszenfire.domain.model.FoodItem
import net.pro.fitnesszenfire.domain.model.Restaurant

interface HomeRepository {

    suspend fun getRestaurants() : Results<List<Restaurant>>
    suspend fun getAds(): Results<List<Advertisement>>
    suspend fun getFoodItems():Results<List<FoodItem>>
    fun getRestaurantFromName(name: String): Restaurant?

}