package com.narvatov.datingapp.ui.viewmodel.signup

import androidx.lifecycle.ViewModel
import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.R
import com.narvatov.datingapp.model.local.Interest
import com.narvatov.datingapp.ui.navigation.SignUpFlow
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.navigate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class SignUpViewModel : ViewModel() {

    private val _interestsStateFlow = MutableStateFlow(interestList)
    val interestsStateFlow = _interestsStateFlow.asStateFlow()

    fun credentialsFilled(email: String, password: String) {
        navigate(SignUpFlow.Gender)
    }

    fun genderSelected(gender: String) {
        navigate(SignUpFlow.Interests)
    }

    fun interestsSelected(interests: List<Interest>) {
        navigate(SignUpFlow.Details)
    }

    fun updateInterestSelection(interest: Interest) = launchCatching {
        _interestsStateFlow.emit(
            _interestsStateFlow.first().map {
                if (it === interest) {
                    interest.copy(selected = interest.selected.not())
                } else it
            }
        )
    }

}

private val interestList = listOf(
    Interest(R.drawable.send, R.string.gender),
    Interest(R.drawable.more, R.string.continue_t),
    Interest(R.drawable.send, R.string.email),
    Interest(R.drawable.filter, R.string.password),
    Interest(R.drawable.back, R.string.fake_photo),
    Interest(R.drawable.send, R.string.gender),
    Interest(R.drawable.more, R.string.continue_t),
    Interest(R.drawable.send, R.string.email),
    Interest(R.drawable.filter, R.string.password),
    Interest(R.drawable.back, R.string.fake_photo),
    Interest(R.drawable.send, R.string.gender),
    Interest(R.drawable.more, R.string.continue_t),
    Interest(R.drawable.send, R.string.email),
    Interest(R.drawable.filter, R.string.password),
    Interest(R.drawable.back, R.string.fake_photo),
    Interest(R.drawable.send, R.string.gender),
    Interest(R.drawable.more, R.string.continue_t),
    Interest(R.drawable.send, R.string.email),
    Interest(R.drawable.filter, R.string.password),
    Interest(R.drawable.back, R.string.fake_photo),
)