package com.narvatov.datingapp.ui.screen.sign

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.ui.common.WideButton
import com.narvatov.datingapp.ui.theme.Typography
import com.narvatov.datingapp.ui.viewmodel.sign.SignUpViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun SignUp(
    viewModel: SignUpViewModel = getViewModel()
) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 40.dp)
        .verticalScroll(scrollState)
    ) {
        Spacer(Modifier.height(50.dp))

        Text(
            text = stringResource(R.string.new_account),
            style = Typography.h3,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        var firstName by rememberSaveable { mutableStateOf("") }

        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = {
                Text(text = stringResource(R.string.first_name))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            modifier = Modifier.padding(top = 40.dp).fillMaxWidth()
        )

        var lastName by rememberSaveable { mutableStateOf("") }

        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = {
                Text(text = stringResource(R.string.last_name))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = true,
            modifier = Modifier.padding(top = 20.dp).fillMaxWidth()
        )

        var email by rememberSaveable { mutableStateOf("") }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text(text = stringResource(R.string.email))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier.padding(top = 20.dp).fillMaxWidth()
        )

        var password by rememberSaveable { mutableStateOf("") }

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = {
                Text(text = stringResource(R.string.password))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            modifier = Modifier.padding(top = 20.dp).fillMaxWidth()
        )

        WideButton(
            text = stringResource(R.string.sign_up),
            modifier = Modifier.padding(top = 40.dp)
        ) { viewModel.signUp(email, password, firstName, lastName) }

        ErrorText(
            errorViewModel = viewModel,
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
        )

        Spacer(Modifier.height(100.dp))
    }
}