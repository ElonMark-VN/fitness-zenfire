package net.pro.fitnesszenfire.presentation.onboarding

sealed class OnBoardingEvent {
    data class CompleteOnboarding (val completeOnBoarding: () -> Unit) : OnBoardingEvent()
}