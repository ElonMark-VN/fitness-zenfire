package net.pro.fitnesszenfire.presentation.home

import net.pro.fitnesszenfire.domain.model.Restaurant

sealed class HomeScreenEvent {
    data class SelectRestaurant(val restaurant: Restaurant, val onClick: () -> Unit) :
        HomeScreenEvent()
}