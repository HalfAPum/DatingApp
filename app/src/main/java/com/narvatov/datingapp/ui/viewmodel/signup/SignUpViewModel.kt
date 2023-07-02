package com.narvatov.datingapp.ui.viewmodel.signup

import androidx.lifecycle.ViewModel
import com.narvatov.datingapp.ui.navigation.SignUpFlow
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.navigate
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SignUpViewModel : ViewModel() {

    fun credentialsFilled(email: String, password: String) {
        navigate(SignUpFlow.Gender)
    }

    fun genderSelected(gender: String) {
        navigate(SignUpFlow.Interests)
    }

}