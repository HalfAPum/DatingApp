package com.narvatov.datingapp.ui.navigation

import kotlinx.coroutines.flow.MutableSharedFlow

object UiNavigationEventPropagator {

    val navigationEvents = MutableSharedFlow<Destination>(extraBufferCapacity = 1)

    private fun MutableSharedFlow<Destination>.navigate(destination: Destination) {
        println("NAVIGATOR LOGGER NAVIGATE TO ${destination.route}")
        tryEmit(destination)
    }

    fun navigate(destination: Destination) {
        navigationEvents.navigate(destination)
    }

    fun popBack(destination: Destination, inclusive: Boolean = false) {
        navigationEvents.navigate(Back.withParam(destination, inclusive))
    }

    fun popBack() {
        navigationEvents.navigate(Back)
    }

}