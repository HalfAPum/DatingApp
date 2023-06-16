package com.narvatov.datingapp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.narvatov.datingapp.ui.screen.connect.Connect
import com.narvatov.datingapp.ui.screen.messages.Messages
import com.narvatov.datingapp.ui.screen.messages.chat.Chat
import com.narvatov.datingapp.ui.screen.profile.FriendProfile
import com.narvatov.datingapp.ui.screen.profile.UserProfile
import com.narvatov.datingapp.ui.screen.sign.SignIn
import com.narvatov.datingapp.ui.screen.sign.SignUp
import com.narvatov.datingapp.ui.viewmodel.PhotoViewModel
import com.narvatov.datingapp.ui.viewmodel.messages.chat.ChatViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NavHostContent(
    navController: NavHostController,
    photoViewModel: PhotoViewModel,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = SignIn,
        modifier = Modifier.padding(innerPadding),
    ) {
        composableNavigationHandler(navController)

        composable(SignIn) {
            SignIn()
        }

        composable(SignUp) {
            SignUp(photoViewModel = photoViewModel)
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
            argument = navArgument(Chat.FRIEND_ID) { type = NavType.StringType }
        ) { backStackEntry ->
            val fiendId = backStackEntry.arguments?.getString(Chat.FRIEND_ID)

            val chatViewModel: ChatViewModel = getViewModel(parameters = { parametersOf(fiendId) })

            Chat(viewModel = chatViewModel)
        }
    }
}