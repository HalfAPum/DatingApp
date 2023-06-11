package com.narvatov.datingapp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.bottomSheetVisibilityEvents
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.hidePhotoBottomSheet
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.navigationEvents
import com.narvatov.datingapp.ui.screen.messages.Messages
import com.narvatov.datingapp.ui.screen.messages.chat.Chat
import com.narvatov.datingapp.ui.screen.profile.Profile
import com.narvatov.datingapp.ui.screen.sign.SignIn
import com.narvatov.datingapp.ui.screen.sign.SignUp
import com.narvatov.datingapp.ui.viewmodel.PhotoViewModel
import com.narvatov.datingapp.ui.viewmodel.messages.chat.ChatViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NavHostContent(
    navController: NavHostController,
    photoViewModel: PhotoViewModel,
    innerPadding: PaddingValues
) = with(navController) {
    val scope = rememberCoroutineScope()

    val isBottomSheetVisible by bottomSheetVisibilityEvents.collectAsState(false)

    NavHost(
        navController = navController,
        startDestination = SignIn,
        modifier = Modifier.padding(innerPadding),
    ) {
        scope.launch {
            navigationEvents.collectLatest { destination ->
                if (isBottomSheetVisible) {
                    hidePhotoBottomSheet()

                    return@collectLatest
                }

                when(destination) {
                    is NavigateWithPopInclusive -> {
                        navigate(destination.navigateDestination) {
                            popUpTo(destination.popToInclusive) {
                                inclusive = true
                            }
                        }
                    }
                    is BackWithParam -> {
                        popBackStack(
                            destination = destination.back,
                            inclusive = destination.inclusive,
                        )
                    }
                    Back -> popBackStack()
                    else -> {
                        val poppedSuccessfully = popBackStack(
                            destination = destination,
                            inclusive = false,
                        )

                        if (poppedSuccessfully) return@collectLatest

                        if (destination is NavigateWithParam) {
                            navigate(destination.destination, destination.param)
                        } else {
                            navigate(destination)
                        }
                    }
                }

            }
        }

        bottomNavigation {
            composable(BottomNavigationDestination.Messages) {
                Messages()
            }

            composable(BottomNavigationDestination.Profile) {
                Profile()
            }
        }

        composable(SignIn) {
            SignIn()
        }

        composable(SignUp) {
            SignUp(photoViewModel = photoViewModel)
        }

        composable(
            destination = Chat,
            argument = navArgument(Chat.USER_ID) { type = NavType.StringType }
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString(Chat.USER_ID)

            val chatViewModel: ChatViewModel = getViewModel(parameters = { parametersOf(userId) })

            Chat(viewModel = chatViewModel)
        }
    }
}