package com.narvatov.datingapp.ui.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.animation.with
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.navigate
import com.narvatov.datingapp.ui.theme.PrimaryColor
import com.narvatov.datingapp.ui.theme.SoftGrey
import com.narvatov.datingapp.ui.theme.SuperSoftGrey

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomBar(
    navController: NavHostController,
    bottomBarState: Boolean,
) = Box(modifier = Modifier.border(width = 1.dp, color = SoftGrey)) {
    AnimatedContent(
        targetState = bottomBarState,
        transitionSpec = {
            slideVerticallyFromBottom(animationSpec = tween(220, delayMillis = 90)) with
                    slideVerticallyToBottom(animationSpec = tween(220, delayMillis = 90))
        }) { isBottomBarVisible ->
        if (isBottomBarVisible) {
            BottomNavigation(backgroundColor = Color.White) {
                /**
                 * This lines needed only to update composable when nev destination moves to top.
                 */
                //Crutch part1 start
                val backStack = navController.currentBackStackEntryAsState()
                //Crutch part1 end

                val selectedRoute = navController.backQueue.asReversed().firstOrNull { entry ->
                    bottomNavigationDestinations.any { bottomNavItem ->
                        entry.destination.route == bottomNavItem.route
                    }
                }?.destination?.route ?: deeplinkDestinationToBottomNavigationDestination[
                        deeplinkDestinationToBottomNavigationDestination.keys.firstOrNull {
                            navController.backQueue.map { it.destination.route }.contains(it)
                        }
                ]?.route

                bottomNavigationDestinations.forEach { destination ->
                    val selected = destination.route == selectedRoute

                    //Crutch part2 start
                    println("NAVIGATOR LOGGER Current top destination is ${backStack.value?.destination?.route}")
                    //Crutch part2 end

                    BottomNavigationItem(
                        icon = {
                            Icon(
                                imageVector = destination.icon,
                                contentDescription = stringResource(destination.text)
                            )
                        },
                        selectedContentColor = PrimaryColor,
                        unselectedContentColor = SuperSoftGrey,
                        label = { Text(stringResource(destination.text)) },
                        selected = destination.route == selectedRoute,
                        onClick = {
                            if (selected) return@BottomNavigationItem

                            // First navigation strategy
                            // Pop back to destination if it exists in stack
                            // Each bottom nav destination have its mini back stack
//                val poppedSuccessfully = navController.popBackStack(destination, inclusive = false)
//
//                if (poppedSuccessfully) return@BottomNavigationItem
//
//                navigate(destination)


                            // Second navigation strategy
                            // no mini back stack for tabs is left
                            // when you navigate to next bottom nav item
                            var popNextDestination = false
                            bottomNavigationDestinations.reversed().forEach {
                                if (it.route == destination.route) {
                                    popNextDestination = true
                                } else if (popNextDestination) {
                                    navController.popBackStack(destination = it, inclusive = false)
                                    popNextDestination = false
                                }
                            }

                            navigate(destination)
                        }
                    )
                }
            }
        }
    }
}

fun slideVerticallyFromBottom(animationSpec: FiniteAnimationSpec<IntOffset>): EnterTransition =
    slideIn(
        initialOffset = { IntOffset(0, 0) },
        animationSpec = animationSpec
    )


fun slideVerticallyToBottom(animationSpec: FiniteAnimationSpec<IntOffset>): ExitTransition =
    slideOut(
        targetOffset = { IntOffset(0, 0) },
        animationSpec = animationSpec
    )
