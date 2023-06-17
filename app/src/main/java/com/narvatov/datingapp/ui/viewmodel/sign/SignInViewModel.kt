package com.narvatov.datingapp.ui.viewmodel.sign

import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.R
import com.narvatov.datingapp.data.repository.sign.SignRepository
import com.narvatov.datingapp.model.local.user.UserAuth
import com.narvatov.datingapp.ui.navigation.BottomNavigationDestination
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.navigate
import com.narvatov.datingapp.ui.viewmodel.ErrorViewModel
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SignInViewModel(private val signRepository: SignRepository): ErrorViewModel() {

    init {
        launchCatching {
            signRepository.signInSavedUser()
        }
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
                signRepository.signIn(UserAuth(email, password))

                navigate(BottomNavigationDestination.Messages, clearBackStack = true)
            }
        }
    }

}