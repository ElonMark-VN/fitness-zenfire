package net.pro.fitnesszenfire.domain.repository

import kotlinx.coroutines.flow.Flow
import net.pro.fitnesszenfire.domain.model.User


interface ProfileRepository {
    suspend fun updateProfile(user: User): Result<Unit>
    val userData: Flow<User?>
}
