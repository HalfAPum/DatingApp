package com.narvatov.datingapp.ui.navigation

import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.collectLatest

suspend fun composableNavigationHandler(navHostController: NavHostController) = with(navHostController) {
    UiNavigationEventPropagator.navigationEvents.collectLatest { destination ->
        when(destination) {
            is NavigateWithPopInclusive -> {
                navigate(destination.navigateDestination) {
                    popUpTo(destination.popToInclusive) {
                        inclusive = true
                    }
                }
            }
            is BackWithParam -> {
                popBackStack(
                    destination = destination.back,
                    inclusive = destination.inclusive,
                )
            }
            Back -> {
                popBackStack()
            }
            is ClearBackStackDestination -> {
                navigate(destination.destination) {
                    popUpTo(0)
                }
            }
            else -> {
                val poppedSuccessfully = popBackStack(
                    destination = destination,
                    inclusive = false,
                )

                if (poppedSuccessfully) return@collectLatest

                if (destination is NavigateWithParam) {
                    navigate(destination.destination, destination.param)
                } else {
                    navigate(destination)
                }
            }
        }

    }
}