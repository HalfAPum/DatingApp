package com.narvatov.datingapp.ui.viewmodel.sign

import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.R
import com.narvatov.datingapp.data.preference.PreferencesDataStore
import com.narvatov.datingapp.data.repository.UserRepository
import com.narvatov.datingapp.model.local.UserAuth
import com.narvatov.datingapp.ui.navigation.BottomNavigationDestination
import com.narvatov.datingapp.ui.navigation.SignIn
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.navigate
import com.narvatov.datingapp.ui.viewmodel.ErrorViewModel
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SignInViewModel(
    private val preferencesDataStore: PreferencesDataStore,
    private val userRepository: UserRepository,
) : ErrorViewModel() {

    init {
        signInSavedUser()
    }

    private fun signInSavedUser() = launchCatching {
        val userAuth = preferencesDataStore.getUserPreferences()

        if (userAuth.isEmpty) return@launchCatching

        signIn(userAuth.email, userAuth.password)
    }

    fun signIn(email: String, password: String) = launchPrintingError {
        when {
            email.isInValidEmail() -> {
                _errorSharedFlow.emit(context.getString(R.string.invalid_email))
            }
            password.isInValidPassword() -> {
                _errorSharedFlow.emit(context.getString(R.string.password_8_symbols_warn))
            }
            else -> {
                userRepository.signIn(UserAuth(email, password))

                navigate(BottomNavigationDestination.Messages, popToInclusive = SignIn)
            }
        }
    }

}