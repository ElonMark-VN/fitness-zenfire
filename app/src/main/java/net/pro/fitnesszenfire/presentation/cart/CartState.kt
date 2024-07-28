package net.pro.fitnesszenfire.presentation.cart

import net.pro.fitnesszenfire.domain.model.CartItem


data class CartState(
    val list: MutableList<CartItem> = mutableListOf()
)

