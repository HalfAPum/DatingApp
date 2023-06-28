package com.narvatov.datingapp.ui.common.square

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.model.local.user.User

@Composable
fun SquareReportButton(friend: User, modifier: Modifier = Modifier) {
    SquareIconButton(
        iconRes = R.drawable.more,
        modifier = Modifier.size(48.dp).then(modifier)
    ) {

    }
}