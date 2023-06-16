package com.narvatov.datingapp.ui.navigation

import com.narvatov.datingapp.model.local.user.User
import kotlinx.coroutines.flow.MutableSharedFlow

object UiNavigationEventPropagator {

    val navigationEvents = MutableSharedFlow<Destination>(extraBufferCapacity = 1)
    val bottomSheetVisibilityEvents = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)

    private fun MutableSharedFlow<Destination>.navigate(destination: Destination) {
        println("NAVIGATOR LOGGER NAVIGATE TO ${destination.route}")
        tryEmit(destination)
    }

    fun navigate(destination: Destination) {
        navigationEvents.navigate(destination)
    }

    fun navigate(destination: FriendProfile, friend: User) {
        destination.friend = friend
        navigate(destination)
    }

    fun navigate(destination: Destination, navigationParam: Any) {
        navigationEvents.navigate(NavigateWithParam(destination, navigationParam))
    }

    fun navigate(destination: Destination, popToInclusive: Destination) {
        navigationEvents.navigate(NavigateWithPopInclusive(destination, popToInclusive))
    }

    fun popBack(destination: Destination, inclusive: Boolean = false) {
        navigationEvents.navigate(Back.withParam(destination, inclusive))
    }

    fun popBack() {
        navigationEvents.navigate(Back)
    }

    fun hidePhotoBottomSheet() {
        bottomSheetVisibilityEvents.tryEmit(false)
    }

    fun showPhotoBottomSheet() {
        bottomSheetVisibilityEvents.tryEmit(true)
    }

}