package com.narvatov.datingapp.ui.viewmodel.sign

import androidx.lifecycle.ViewModel
import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.R
import com.narvatov.datingapp.data.repository.sign.SignRepository
import com.narvatov.datingapp.model.local.action.Action
import com.narvatov.datingapp.model.local.user.UserAuth
import com.narvatov.datingapp.ui.navigation.BottomNavigationDestination
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.actionFlow
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.navigate
import com.narvatov.datingapp.ui.viewmodel.delegate.error.ErrorDelegate
import com.narvatov.datingapp.ui.viewmodel.delegate.error.IErrorDelegate
import com.narvatov.datingapp.ui.viewmodel.delegate.progress.IProgressDelegate
import com.narvatov.datingapp.ui.viewmodel.delegate.progress.ProgressDelegate
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SignInViewModel(private val signRepository: SignRepository): ViewModel(),
    IErrorDelegate by ErrorDelegate(), IProgressDelegate by ProgressDelegate() {

    init {
        launchCatching {
            signRepository.signInSavedUser()
        }
    }

    fun signIn(email: String, password: String) = launchPrintingError {
        when {
            email.isInValidEmail() -> {
                errorSharedFlow.emit(context.getString(R.string.invalid_email))
            }
            password.isInValidPassword() -> {
                errorSharedFlow.emit(context.getString(R.string.password_8_symbols_warn))
            }
            else -> {
                signRepository.signIn(UserAuth(email, password))

                navigate(BottomNavigationDestination.Messages, clearBackStack = true)

                actionFlow.emit(Action.SignAction.SignInAction)
            }
        }
    }

}