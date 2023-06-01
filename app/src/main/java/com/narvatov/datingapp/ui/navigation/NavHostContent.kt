package com.narvatov.datingapp.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.navigationEvents
import com.narvatov.datingapp.ui.screen.sign.SignIn
import com.narvatov.datingapp.ui.screen.sign.SignUp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun NavHostContent(
    navController: NavHostController,
    innerPadding: PaddingValues
) = with(navController) {
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = SignIn,
        modifier = Modifier.padding(innerPadding),
    ) {
        scope.launch {
            navigationEvents.collectLatest { destination ->
                when(destination) {
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

                        navigate(destination)
                    }
                }

            }
        }

        bottomNavigation {

        }

        composable(SignIn) {
            SignIn()
        }

        composable(SignUp) {
            SignUp()
        }
    }
}