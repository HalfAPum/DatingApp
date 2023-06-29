package com.narvatov.datingapp.ui.screen.filter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.ui.common.button.WideButton
import com.narvatov.datingapp.ui.common.button.WideButtonSecondary
import com.narvatov.datingapp.ui.common.header.TextHeader
import com.narvatov.datingapp.ui.screen.filter.child.AgeFilter
import com.narvatov.datingapp.ui.screen.filter.child.GenderFilter

@Composable
fun ConnectFilter() {
    Column(modifier = Modifier
        .padding(top = 20.dp)
    ) {
        TextHeader(R.string.filter, modifier = Modifier.padding(horizontal = 30.dp))

        GenderFilter(modifier = Modifier.padding(top = 20.dp))

        AgeFilter(modifier = Modifier.padding(top = 16.dp).padding(horizontal = 20.dp))

        WideButton(
            text = stringResource(R.string.continue_t),
            modifier = Modifier.padding(top = 60.dp).padding(horizontal = 30.dp),
        ) {

        }

        WideButtonSecondary(
            text = stringResource(R.string.clear_filter),
            modifier = Modifier.padding(top = 20.dp).padding(horizontal = 30.dp),
        ) {

        }
    }
}