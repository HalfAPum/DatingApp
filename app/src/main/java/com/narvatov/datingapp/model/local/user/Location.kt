package com.narvatov.datingapp.model.local.user

data class Location(
    private val latitude: Double,
    private val longitude: Double,
) {

    val isEmpty: Boolean
        get() = equals(emptyLocation)

    companion object {
        val emptyLocation = Location(0.0,0.0)
    }
}