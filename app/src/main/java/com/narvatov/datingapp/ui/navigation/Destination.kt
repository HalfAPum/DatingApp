package com.narvatov.datingapp.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.ConnectWithoutContact
import androidx.compose.material.icons.rounded.Message
import androidx.compose.ui.graphics.vector.ImageVector
import com.narvatov.datingapp.R

interface Destination {

    val simpleRoute: String
        get() = javaClass.simpleName

    val route: String
        get() = simpleRoute + navigationParamInner

    val navigationParamInner: String
        get() = if (navigationParam.isBlank()) "" else "/{$navigationParam}"

    val navigationParam: String
        get() = ""

}

class NavigateWithPopInclusive(
    val navigateDestination: Destination,
    val popToInclusive: Destination,
) : Destination

class NavigateWithParam(
    val destination: Destination,
    val param: Any,
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

    object Connect : BottomNavigationDestination(
        icon = Icons.Rounded.ConnectWithoutContact,
        text = R.string.connect,
    )

    object Profile : BottomNavigationDestination(
        icon = Icons.Rounded.AccountCircle,
        text = R.string.profile,
    )

}

val bottomNavigationDestinations = listOf(
    BottomNavigationDestination.Messages,
    BottomNavigationDestination.Connect,
    BottomNavigationDestination.Profile,
)

sealed class DialogDestination(
    val dismissOnBackPress: Boolean = true,
    val dismissOnClickOutside: Boolean = true,
) : Destination

object SignIn : Destination

object SignUp : Destination

object Chat : Destination {

    override val navigationParam: String
        get() = FRIEND_ID

    const val FRIEND_ID = "fiendId"

}

val noBottomBarDestinations = listOf(SignIn, SignUp, Chat).map { it.route }