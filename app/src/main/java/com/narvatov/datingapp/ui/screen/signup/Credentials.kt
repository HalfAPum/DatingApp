package com.narvatov.datingapp.ui.screen.signup

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.ui.common.labeled.texfield.OutlinedLabeledTextField
import com.narvatov.datingapp.ui.viewmodel.signup.SignUpViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun Credentials(viewModel: SignUpViewModel = getViewModel()) {
    val focusManager = LocalFocusManager.current

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    val credentialsFilledAction = {
        focusManager.clearFocus()
        viewModel.credentialsFilled(email, password)
    }

    SignUpFlowColumn(
        topTextRes = R.string.credentials,
        continueAction = credentialsFilledAction,
    ) {
        OutlinedLabeledTextField(
            value = email,
            onValueChange = { email = it },
            labelRes = R.string.email,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            modifier = Modifier.padding(top = 50.dp),
        )

        OutlinedLabeledTextField(
            value = password,
            onValueChange = { password = it },
            labelRes = R.string.password,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                credentialsFilledAction.invoke()
            }),
            modifier = Modifier.padding(top = 12.dp),
        )
    }
}