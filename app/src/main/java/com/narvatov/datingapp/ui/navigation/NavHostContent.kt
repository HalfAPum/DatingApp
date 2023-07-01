package com.narvatov.datingapp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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
import com.narvatov.datingapp.ui.screen.profile.FriendProfile
import com.narvatov.datingapp.ui.screen.profile.UserProfile
import com.narvatov.datingapp.ui.screen.report.Report
import com.narvatov.datingapp.ui.screen.sign.SignIn
import com.narvatov.datingapp.ui.screen.sign.SignUp
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
        startDestination = ConnectFilter,
        modifier = Modifier.padding(innerPadding),
    ) {
        scope.launch {
            composableNavigationHandler(navController)
        }

        composable(SignIn) {
            SignIn()
        }

        composable(SignUp) {
            SignUp(photoViewModel = getViewModel(owner = activityViewModelStoreOwner))
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
            argument = Chat.navigationArgument,
        ) { backStackEntry ->
            val friendId = backStackEntry.arguments?.getString(Report.FRIEND_ID)

            friendId?.let { Report(friendId) }
        }
    }
}