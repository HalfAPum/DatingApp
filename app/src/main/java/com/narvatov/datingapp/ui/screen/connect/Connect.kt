package com.narvatov.datingapp.ui.screen.connect

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alexstyl.swipeablecard.rememberSwipeableCardState
import com.narvatov.datingapp.R
import com.narvatov.datingapp.ui.WeightedSpacer
import com.narvatov.datingapp.ui.common.SquareIconButton
import com.narvatov.datingapp.ui.theme.Typography
import com.narvatov.datingapp.ui.viewmodel.connect.ConnectViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun Connect(viewModel: ConnectViewModel = getViewModel()) {
    Column {
        Row(modifier = Modifier
            .padding(horizontal = 30.dp)
            .padding(top = 20.dp)
            .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.discover),
                style = Typography.h4,
            )

            WeightedSpacer()

            SquareIconButton(
                iconRes = R.drawable.filter,
                modifier = Modifier.size(50.dp)
            ) {

            }
        }

        val newFriends by viewModel.newFriendStateFlow.collectAsState()

        val states = newFriends.associateWith { rememberSwipeableCardState() }

        states.forEach { (friend, state) ->
            ConnectCard(friend, state, viewModel)
        }
    }
}