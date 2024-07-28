package net.pro.fitnesszenfire.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.pro.fitnesszenfire.PrefsDataStore
import net.pro.fitnesszenfire.domain.repository.OnBoardingRepository

class OnboardingRepositoryImpl(context: Context) :
    PrefsDataStore(context = context, PREF_ONBOARDING_STATE),
    OnBoardingRepository {

    companion object {
        const val PREF_ONBOARDING_STATE = "onboarding_state_pref"
        private val ONBOARDING_STATE_KEY = booleanPreferencesKey("onboarding_completed")
    }

    override val onBoardingState: Flow<Boolean>
        get() = mDataStore.data.map { preferences ->
            preferences[ONBOARDING_STATE_KEY] ?: false
        }


    override suspend fun toggleOnBoardingState() {
        mDataStore.edit { preferences ->
            preferences[ONBOARDING_STATE_KEY] = true
        }
    }

}
