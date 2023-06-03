package com.narvatov.datingapp.ui.viewmodel.sign

import com.narvatov.datingapp.R
import com.narvatov.datingapp.data.repository.UserRepository
import com.narvatov.datingapp.model.local.UserAuth
import com.narvatov.datingapp.ui.viewmodel.ErrorViewModel
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SignInViewModel(
    private val userRepository: UserRepository,
) : ErrorViewModel() {

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

                navigate()
            }
        }
    }

}