package com.narvatov.datingapp.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Message
import androidx.compose.ui.graphics.vector.ImageVector
import com.narvatov.datingapp.R

interface Destination {

    val route: String
        get() = javaClass.simpleName ?: ""

}

class NavigateWithPopInclusive(
    val navigateDestination: Destination,
    val popToInclusive: Destination,
) : Destination

object Back : Destination {

    fun withParam(destination: Destination, inclusive: Boolean): BackWithParam {
        return BackWithParam(destination, inclusive)
    }
}

class BackWithParam(
    val back: Destination,
    val inclusive: Boolean
) : Destination

sealed class BottomNavigationDestination(
    val icon: ImageVector,
    @StringRes
    val text: Int,
): Destination {

    object Messages : BottomNavigationDestination(
        icon = Icons.Rounded.Message,
        text = R.string.messages,
    )

    object Profile : BottomNavigationDestination(
        icon = Icons.Rounded.AccountCircle,
        text = R.string.profile,
    )

}

val bottomNavigationDestinations = listOf(
    BottomNavigationDestination.Messages,
    BottomNavigationDestination.Profile,
)

sealed class DialogDestination(
    val dismissOnBackPress: Boolean = true,
    val dismissOnClickOutside: Boolean = true,
) : Destination

object SignIn : Destination

object SignUp : Destination

val noBottomBarDestinations = listOf(SignIn, SignUp).map { it.route }