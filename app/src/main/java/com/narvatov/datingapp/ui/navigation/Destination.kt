package com.narvatov.datingapp.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.narvatov.datingapp.R

interface Destination {

    val route: String
        get() = javaClass.simpleName ?: ""

}

object Back : Destination {

    fun withParam(destination: Destination, inclusive: Boolean): BackWithParam {
        return BackWithParam(destination, inclusive)
    }
}

class BackWithParam(
    val back: Destination,
    val inclusive: Boolean
) : Destination

sealed class HeaderDestination(
    route: String,
    val headerText: String,
    val canGoBack: Boolean = true
) : Destination

sealed class BottomNavigation(
    name: String,
    headerText: String,
    @DrawableRes
    val icon: Int,
    @StringRes
    val text: Int,
): HeaderDestination(
    route = name,
    headerText = headerText,
    canGoBack = false
)

val bottomNavigationItems = emptyList<BottomNavigation>(
//    BottomNavigation.Tasks,
//    BottomNavigation.Plants,
//    BottomNavigation.Settings,
)

sealed class DialogDestination(
    route: String,
    val dismissOnBackPress: Boolean = true,
    val dismissOnClickOutside: Boolean = true,
) : Destination

object SignIn : Destination

object SignUp : Destination

val noBottomBarDestinations = listOf(SignIn, SignUp).map { it.route }