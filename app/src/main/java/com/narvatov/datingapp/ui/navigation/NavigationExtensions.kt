package com.narvatov.datingapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.PopUpToBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog

fun NavGraphBuilder.bottomNavigation(builder: NavGraphBuilder.() -> Unit) {
    builder()
}

fun NavGraphBuilder.composable(
    destination: Destination,
    argument: NamedNavArgument? = null,
    content: @Composable (NavBackStackEntry) -> Unit,
) {
    composable(
        route = destination.route,
        arguments = if (argument == null) emptyList() else listOf(argument),
        deepLinks = emptyList(),
        content = content,
    )
}

fun NavGraphBuilder.dialog(
    destination: DialogDestination,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    dialog(
        route = destination.route,
        dialogProperties = DialogProperties(
            dismissOnBackPress = destination.dismissOnBackPress,
            dismissOnClickOutside = destination.dismissOnClickOutside,
        ),
        content = content,
    )
}

@Composable
fun NavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier,
    builder: NavGraphBuilder.() -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = modifier,
        builder = builder
    )
}

fun NavHostController.navigate(
    destination: Destination,
    param: Any? = null,
    builder: NavOptionsBuilder.() -> Unit = {},
) {
    val route = if (param == null) destination.simpleRoute else "${destination.simpleRoute}/$param"

    navigate(route) {
        launchSingleTop = true
        restoreState = true

        builder()
    }
}

fun NavOptionsBuilder.popUpTo(
    destination: Destination,
    popUpToBuilder: PopUpToBuilder.() -> Unit = {}
) {
    popUpTo(destination.route, popUpToBuilder)
}

fun NavHostController.popBackStack(
    destination: Destination,
    inclusive: Boolean,
) = popBackStack(destination.route, inclusive)

fun NavController.getBackStackEntryNullable(
    destination: Destination
) = kotlin.runCatching { getBackStackEntry(destination.route) }.getOrNull()

fun NavController.getBackStackEntry(
    destination: Destination
) = getBackStackEntry(destination.route)

val String?.showBottomBar: Boolean
    get() = noBottomBarDestinations.contains(this).not()