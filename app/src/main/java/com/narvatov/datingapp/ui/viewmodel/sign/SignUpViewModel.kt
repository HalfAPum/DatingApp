package com.narvatov.datingapp.ui.viewmodel.sign

import android.graphics.Bitmap
import com.narvatov.datingapp.R
import com.narvatov.datingapp.data.repository.sign.SignRepository
import com.narvatov.datingapp.model.local.user.NewUser
import com.narvatov.datingapp.ui.navigation.BottomNavigationDestination
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.navigate
import com.narvatov.datingapp.ui.viewmodel.ErrorViewModel
import com.narvatov.datingapp.utils.toBase64
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SignUpViewModel(private val signRepository: SignRepository): ErrorViewModel() {

    fun signUp(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        photoBitmap: Bitmap?
    ) = launchPrintingError {
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
            photoBitmap == null -> {
                _errorSharedFlow.emit(context.getString(R.string.please_pick_photo_for_your_profile))
            }
            else -> {
                signRepository.signUp(NewUser(email, password, firstName, lastName, photoBitmap.toBase64))

                navigate(BottomNavigationDestination.UserProfile, clearBackStack = true)
            }
        }
    }

}