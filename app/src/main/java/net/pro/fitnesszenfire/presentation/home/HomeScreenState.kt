package net.pro.fitnesszenfire.presentation.home

import net.pro.fitnesszenfire.domain.model.Advertisement
import net.pro.fitnesszenfire.domain.model.FoodItem
import net.pro.fitnesszenfire.domain.model.Restaurant

data class HomeScreenState(
    val adsList: List<Advertisement> = emptyList(),
    val foodList: List<FoodItem> = emptyList(),
    val likedRestaurantList : List<Restaurant> = emptyList(),
    val restaurantList : List<Restaurant> = emptyList(),
)