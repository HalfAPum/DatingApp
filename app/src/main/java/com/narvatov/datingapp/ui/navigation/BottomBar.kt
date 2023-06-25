package com.narvatov.datingapp.ui.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.animation.with
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.narvatov.datingapp.ui.common.NO_NEW_CONVERSATION_MESSAGES
import com.narvatov.datingapp.ui.common.NewMessagesBadge
import com.narvatov.datingapp.ui.common.enterBadgeAnimation
import com.narvatov.datingapp.ui.common.exitBadgeAnimation
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.navigate
import com.narvatov.datingapp.ui.theme.PrimaryColor
import com.narvatov.datingapp.ui.theme.SoftGrey
import com.narvatov.datingapp.ui.theme.SuperSoftGrey
import com.narvatov.datingapp.ui.viewmodel.BottomBarViewModel
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomBar(
    navController: NavHostController,
    bottomBarState: Boolean,
    viewModel: BottomBarViewModel = getViewModel(),
) = Box(modifier = Modifier.border(width = 1.dp, color = SoftGrey)) {
    AnimatedContent(
        targetState = bottomBarState,
        transitionSpec = {
            slideVerticallyFromBottom(animationSpec = tween(220, delayMillis = 90)) with
                    slideVerticallyToBottom(animationSpec = tween(220, delayMillis = 90))
        }) { isBottomBarVisible ->
        if (isBottomBarVisible) {
            BottomNavigation(backgroundColor = Color.White) {
                val newConversationMessages by viewModel.newConversationMessagesFlow.collectAsState(
                    NO_NEW_CONVERSATION_MESSAGES
                )

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

                AnimatedVisibility(true) {

                }
                bottomNavigationDestinations.forEach { destination ->
                    val selected = destination.route == selectedRoute

                    //Crutch part2 start
                    println("NAVIGATOR LOGGER Current top destination is ${backStack.value?.destination?.route}")
                    //Crutch part2 end

                    BottomNavigationItem(
                        icon = {
                            val increaseBarSize = newConversationMessages > NO_NEW_CONVERSATION_MESSAGES
                                    && selectedRoute != BottomNavigationDestination.Messages.route
                            val containerTransition = updateTransition(increaseBarSize, "BottomBar Item Transition change size")
                            val badgeTransition = updateTransition(destination.route == BottomNavigationDestination.Messages.route
                                    && !selected, "Badge Transition change size")
                            val bottomBarIconSize = 24.dp
                            val halfMessageBadgeSize = 10.dp
                            val containerAnimatedSize by containerTransition.animateDp(label = "ContainerChangeSize") { increaseSize ->
                                if (increaseSize) bottomBarIconSize + halfMessageBadgeSize else bottomBarIconSize
                            }

                            val messageBadgeAnimatedSize by badgeTransition.animateDp(label = "BadgeChangeSize") { increaseSize ->
                                if (increaseSize) halfMessageBadgeSize * 2  else 0.dp
                            }

                            Box(modifier = Modifier.size(containerAnimatedSize)) {
                                Icon(
                                    painter = painterResource(destination.icon),
                                    contentDescription = stringResource(destination.text),
                                    modifier = Modifier.size(bottomBarIconSize)
                                        .align(Alignment.BottomCenter)
                                )

                                androidx.compose.animation.AnimatedVisibility(
                                    visible = increaseBarSize,
                                    enter = enterBadgeAnimation,
                                    exit = exitBadgeAnimation,
                                    modifier = Modifier.align(Alignment.TopEnd)
                                ) {
                                    NewMessagesBadge(
                                        messagesCount = newConversationMessages,
                                        modifier = Modifier
                                            .size(messageBadgeAnimatedSize)
                                            .animateContentSize()
                                    )
                                }
                            }
                        },
                        selectedContentColor = PrimaryColor,
                        unselectedContentColor = SuperSoftGrey,
//                        label = { Text(stringResource(destination.text)) },
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

private fun slideVerticallyFromBottom(animationSpec: FiniteAnimationSpec<IntOffset>): EnterTransition =
    slideIn(
        initialOffset = { IntOffset(0, 0) },
        animationSpec = animationSpec
    )


private fun slideVerticallyToBottom(animationSpec: FiniteAnimationSpec<IntOffset>): ExitTransition =
    slideOut(
        targetOffset = { IntOffset(0, 0) },
        animationSpec = animationSpec
    )