package com.narvatov.datingapp.ui.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.ui.common.WideButton
import com.narvatov.datingapp.ui.viewmodel.profile.UserProfileViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun UserProfile(
    viewModel: UserProfileViewModel = getViewModel()
) {
    val user by viewModel.userFlow.collectAsState()

    Box() {
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
}