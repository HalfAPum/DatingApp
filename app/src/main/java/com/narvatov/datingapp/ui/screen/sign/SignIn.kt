package com.narvatov.datingapp.ui.screen.sign

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.ui.common.ErrorText
import com.narvatov.datingapp.ui.common.WideButton
import com.narvatov.datingapp.ui.navigation.SignUp
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.navigate
import com.narvatov.datingapp.ui.theme.Typography
import com.narvatov.datingapp.ui.viewmodel.sign.SignInViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun SignIn(
    viewModel: SignInViewModel = getViewModel()
) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 40.dp)
        .verticalScroll(scrollState)
    ) {
        Spacer(Modifier.height(100.dp))

        Text(
            text = stringResource(R.string.welcome),
            style = Typography.h3,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        val focusManager = LocalFocusManager.current

        var email by rememberSaveable { mutableStateOf("kitty@gmail.com") }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text(text = stringResource(R.string.email))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            singleLine = true,
            modifier = Modifier.padding(top = 40.dp).fillMaxWidth()
        )

        var password by rememberSaveable { mutableStateOf("123123123") }

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text(text = stringResource(R.string.password))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                viewModel.signIn(email, password)
                focusManager.clearFocus()
            }),
            singleLine = true,
            modifier = Modifier.padding(top = 20.dp).fillMaxWidth()
        )

        WideButton(
            text = stringResource(R.string.sign_in),
            modifier = Modifier.padding(top = 40.dp)
        ) {
            viewModel.signIn(email, password)
            focusManager.clearFocus()
        }

        WideButton(
            text = stringResource(R.string.don_t_have_an_account_sign_up),
            modifier = Modifier.padding(top = 20.dp)
        ) {
            navigate(SignUp)
        }

        ErrorText(
            errorViewModel = viewModel,
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
        )

        Spacer(Modifier.height(100.dp))
    }
}