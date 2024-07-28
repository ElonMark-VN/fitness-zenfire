package net.pro.fitnesszenfire.domain.repository

import kotlinx.coroutines.flow.Flow
import net.pro.fitnesszenfire.domain.model.User

interface LoginRepository {
     suspend fun loginWithFacebook(token: String)
}