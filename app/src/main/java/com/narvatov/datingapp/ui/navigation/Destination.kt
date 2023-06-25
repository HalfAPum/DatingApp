package com.narvatov.datingapp.ui.navigation

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.narvatov.datingapp.R
import com.narvatov.datingapp.model.local.user.User

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

class ClearBackStackDestination(
    val destination: Destination,
    val clearBackStack: Boolean,
) : Destination

sealed class BottomNavigationDestination(
    @DrawableRes
    val icon: Int,
    @StringRes
    val text: Int,
): Destination {

    object Messages : BottomNavigationDestination(
        icon = R.drawable.menu_chat,
        text = R.string.messages,
    )

    object Connect : BottomNavigationDestination(
        icon = R.drawable.menu_friends,
        text = R.string.friends,
    )

    /**
     * [BottomNavigationDestination.UserProfile] represents current users profile.
     */
    object UserProfile : BottomNavigationDestination(
        icon = R.drawable.menu_profile,
        text = R.string.profile,
    )

}

/**
 * [FriendProfile] represents other users profiles.
 */
object FriendProfile : Destination {

    var friend: User? = null

}

val bottomNavigationDestinations = listOf(
    BottomNavigationDestination.Messages,
    BottomNavigationDestination.Connect,
    BottomNavigationDestination.UserProfile,
)

val deeplinkDestinationToBottomNavigationDestination = mapOf(
    ChatDeeplink.simpleRoute to BottomNavigationDestination.Messages
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

    val navigationArgument: NamedNavArgument
        get() = navArgument(FRIEND_ID) { type = NavType.StringType }

}

object ChatDeeplink : Destination {

    override val simpleRoute: String
        get() = super.simpleRoute + deeplinkQuery

    private val deeplinkQuery: String
        get() = "?$FRIEND_ID={$FRIEND_ID}"

    fun getDeeplink(context: Context) = navDeepLink {
        uriPattern = "${context.getString(R.string.chat_deeplink_host)}/{$FRIEND_ID}"
    }

    const val FRIEND_ID = "fiendId"

}

val noBottomBarDestinations = listOf(SignIn, SignUp, Chat, ChatDeeplink).map { it.route }