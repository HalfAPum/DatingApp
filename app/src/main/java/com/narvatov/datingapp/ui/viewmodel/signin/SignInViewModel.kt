package com.narvatov.datingapp.ui.viewmodel.signin

import androidx.lifecycle.ViewModel
import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.R
import com.narvatov.datingapp.data.repository.sign.SignRepository
import com.narvatov.datingapp.model.local.action.Action
import com.narvatov.datingapp.model.local.user.UserAuth
import com.narvatov.datingapp.ui.navigation.BottomNavigationDestination
import com.narvatov.datingapp.ui.navigation.OnBoardingManager
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.actionFlow
import com.narvatov.datingapp.ui.viewmodel.delegate.error.ErrorDelegate
import com.narvatov.datingapp.ui.viewmodel.delegate.error.IErrorDelegate
import com.narvatov.datingapp.ui.viewmodel.delegate.progress.IProgressDelegate
import com.narvatov.datingapp.ui.viewmodel.delegate.progress.ProgressDelegate
import com.narvatov.datingapp.utils.isInValidEmail
import com.narvatov.datingapp.utils.isInValidPassword
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SignInViewModel(
    private val signRepository: SignRepository,
    private val progressDelegate: ProgressDelegate,
    private val onBoardingManager: OnBoardingManager,
): ViewModel(), IErrorDelegate by ErrorDelegate(progressDelegate),
    IProgressDelegate by progressDelegate {

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
                showProgress()

                signRepository.signIn(UserAuth(email, password))

                onBoardingManager.processOnBoarding(BottomNavigationDestination.Messages)

                actionFlow.emit(Action.SignAction.SignInAction)
            }
        }
    }

}