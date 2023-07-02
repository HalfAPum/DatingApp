package com.narvatov.datingapp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import com.narvatov.datingapp.ui.screen.connect.Connect
import com.narvatov.datingapp.ui.screen.filter.ConnectFilter
import com.narvatov.datingapp.ui.screen.messages.Messages
import com.narvatov.datingapp.ui.screen.messages.chat.Chat
import com.narvatov.datingapp.ui.screen.messages.chat.DeeplinkChat
import com.narvatov.datingapp.ui.screen.onboarding.LocationPermissionOnBoarding
import com.narvatov.datingapp.ui.screen.onboarding.NotificationPermissionOnBoarding
import com.narvatov.datingapp.ui.screen.profile.FriendProfile
import com.narvatov.datingapp.ui.screen.profile.UserProfile
import com.narvatov.datingapp.ui.screen.report.Report
import com.narvatov.datingapp.ui.screen.signin.SignIn
import com.narvatov.datingapp.ui.screen.signup.Credentials
import com.narvatov.datingapp.ui.screen.signup.Details
import com.narvatov.datingapp.ui.screen.signup.Gender
import com.narvatov.datingapp.ui.screen.signup.Interests
import com.narvatov.datingapp.ui.screen.signup.SignUp
import com.narvatov.datingapp.ui.screen.start.StartPage
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun NavHostContent(
    navController: NavHostController,
    activityViewModelStoreOwner: ViewModelStoreOwner,
    innerPadding: PaddingValues,
) {
    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = StartPage,
        modifier = Modifier.padding(innerPadding),
    ) {
        scope.launch {
            composableNavigationHandler(navController)
        }

        composable(StartPage) {
            StartPage()
        }

        composable(SignIn) {
            SignIn()
        }

        signUpFlow {
            composable(SignUpFlow.SignUp) {
                SignUp(photoViewModel = getViewModel(owner = activityViewModelStoreOwner))
            }

            composable(SignUpFlow.Credentials) {
                Credentials()
            }

            composable(SignUpFlow.Gender) {
                val viewModelStateOwner = remember {
                    navController.getBackStackEntry(SignUpFlow.Credentials)
                }

                Gender(viewModel = getViewModel(owner = viewModelStateOwner))
            }

            composable(SignUpFlow.Interests) {
                val viewModelStateOwner = remember {
                    navController.getBackStackEntry(SignUpFlow.Credentials)
                }

                Interests(viewModel = getViewModel(owner = viewModelStateOwner))
            }

            composable(SignUpFlow.Details) {
                val viewModelStateOwner = remember {
                    navController.getBackStackEntry(SignUpFlow.Credentials)
                }

                Details(viewModel = getViewModel(owner = viewModelStateOwner))
            }
        }

        onBoardingFlow {
            composable(OnBoardingFlow.NotificationPermissionOnBoarding) {
                NotificationPermissionOnBoarding()
            }

            composable(OnBoardingFlow.LocationPermissionOnBoarding) {
                LocationPermissionOnBoarding()
            }
        }

        bottomNavigation {
            composable(BottomNavigationDestination.Messages) {
                Messages()
            }

            composable(BottomNavigationDestination.Connect) {
                Connect()
            }

            composable(BottomNavigationDestination.UserProfile) {
                UserProfile()
            }
        }

        composable(FriendProfile) {
            FriendProfile(FriendProfile.friend)
        }

        composable(
            destination = Chat,
            argument = Chat.navigationArgument
        ) { backStackEntry ->
            val friendId = backStackEntry.arguments?.getString(Chat.FRIEND_ID)

            Chat(friendId)
        }

        composable(
            destination = ChatDeeplink,
            deepLink = ChatDeeplink.getDeeplink(context)
        ) { backStackEntry ->
            val friendId = backStackEntry.arguments?.getString(ChatDeeplink.FRIEND_ID)

            DeeplinkChat(friendId)
        }

        composable(ConnectFilter) {
            ConnectFilter()
        }

        composable(
            destination = Report,
            argument = Report.navigationArgument,
        ) { backStackEntry ->
            val friendId = backStackEntry.arguments?.getString(Report.FRIEND_ID)

            friendId?.let { Report(friendId) }
        }
    }
}