

package net.pro.fitnesszenfire.presentation.onboarding.util

import net.pro.fitnesszenfire.R

class OnBoardingItem(
    val title: Int,
    val text: Int,
    val image: Int
) {
    companion object {
        fun get(): List<OnBoardingItem> {
            return listOf(
                OnBoardingItem(
                    R.string.onboardingHeading1,
                    R.string.onboardingText1,
                    R.drawable.fitness1
                ),
                OnBoardingItem(
                    R.string.onboardingHeading2,
                    R.string.onboardingText2,
                    R.drawable.fitness2
                ),
                OnBoardingItem(
                    R.string.onboardingHeading3,
                    R.string.onboardingText3,
                    R.drawable.fitness3
                )

            )

        }
    }
}