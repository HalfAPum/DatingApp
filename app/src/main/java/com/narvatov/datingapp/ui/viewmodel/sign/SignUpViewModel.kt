package com.narvatov.datingapp.ui.viewmodel.sign

import com.narvatov.datingapp.R
import com.narvatov.datingapp.data.repository.UserRepository
import com.narvatov.datingapp.model.local.NewUser
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.popBack
import com.narvatov.datingapp.ui.viewmodel.ErrorViewModel
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SignUpViewModel(
    private val userRepository: UserRepository,
) : ErrorViewModel() {

    fun signUp(email: String, password: String, firstName: String, lastName: String) = launchPrintingError {
        when {
            email.isInValidEmail() -> {
                _errorSharedFlow.emit(context.getString(R.string.invalid_email))
            }
            password.isInValidPassword() -> {
                _errorSharedFlow.emit(context.getString(R.string.password_8_symbols_warn))
            }
            firstName.isInValidName() -> {
                _errorSharedFlow.emit(context.getString(R.string.first_name_shouldn_t_be_empty))
            }
            lastName.isInValidName() -> {
                _errorSharedFlow.emit(context.getString(R.string.last_name_shouldn_t_be_empty))
            }
            else -> {
                userRepository.signUp(NewUser(email, password, firstName, lastName))

                popBack()
            }
        }
    }
}