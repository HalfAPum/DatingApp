package com.narvatov.datingapp.ui.screen.profile

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.model.local.user.User
import com.narvatov.datingapp.ui.common.LoaderBox
import com.narvatov.datingapp.ui.common.button.WideButton
import com.narvatov.datingapp.ui.viewmodel.profile.UserProfileViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun UserProfile(
    viewModel: UserProfileViewModel = getViewModel()
) = LoaderBox(viewModel) {
    val user by viewModel.userFlow.collectAsState(User.emptyUser)

    BaseProfile(user) {
        WideButton(
            text = stringResource(R.string.logout),
            modifier = Modifier.padding(top = 20.dp)
        ) { viewModel.logout() }

        WideButton(
            text = stringResource(R.string.delete_account),
            modifier = Modifier.padding(top = 20.dp)
        ) { viewModel.deleteAccount() }
    }
}