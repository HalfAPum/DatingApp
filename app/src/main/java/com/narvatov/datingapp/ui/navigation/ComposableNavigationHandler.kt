package com.narvatov.datingapp.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun composableNavigationHandler(navHostController: NavHostController) = with(navHostController) {
    val scope = rememberCoroutineScope()

    val isBottomSheetVisible by UiNavigationEventPropagator.bottomSheetVisibilityEvents.collectAsState(false)

    scope.launch {
        UiNavigationEventPropagator.navigationEvents.collectLatest { destination ->
            if (isBottomSheetVisible) {
                UiNavigationEventPropagator.hidePhotoBottomSheet()

                return@collectLatest
            }

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
                Back -> popBackStack()
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
}