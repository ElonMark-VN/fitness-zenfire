package net.pro.fitnesszenfire.data.repository


import net.pro.fitnesszenfire.data.data_source.adList
import net.pro.fitnesszenfire.data.data_source.recommendedList
import net.pro.fitnesszenfire.data.data_source.restaurantList
import net.pro.fitnesszenfire.domain.model.Advertisement
import net.pro.fitnesszenfire.domain.model.FoodItem
import net.pro.fitnesszenfire.domain.model.Restaurant
import net.pro.fitnesszenfire.domain.repository.HomeRepository

class HomeRepositoryImpl() : HomeRepository {

    override suspend fun getRestaurants(): Results<List<Restaurant>> {
        return Results.Success(restaurantList)
    }

    override suspend fun getAds(): Results<List<Advertisement>> {
        return Results.Success(adList)
    }

    override suspend fun getFoodItems(): Results<List<FoodItem>> {
        return Results.Success(recommendedList)
    }

    override fun getRestaurantFromName(name: String): Restaurant? {
        return restaurantList.find {
            it.name == name
        }
    }
}