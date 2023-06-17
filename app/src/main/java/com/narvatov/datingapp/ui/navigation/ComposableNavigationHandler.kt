package com.narvatov.datingapp.ui.navigation

import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun composableNavigationHandler(
    coroutineScope: CoroutineScope,
    isBottomSheetVisible: Boolean,
    navHostController: NavHostController
) = with(navHostController) {
    coroutineScope.launch {
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
}