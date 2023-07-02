package com.narvatov.datingapp.ui.screen.signup

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.ui.WeightedSpacer
import com.narvatov.datingapp.ui.common.button.WideButton
import com.narvatov.datingapp.ui.theme.Typography
import com.narvatov.datingapp.utils.UnitCallback

@Composable
fun SignUpFlowColumn(
    @StringRes
    topTextRes: Int,
    continueAction: UnitCallback,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(modifier = Modifier.padding(horizontal = 30.dp).padding(top = 100.dp)) {
        Text(
            text = stringResource(topTextRes),
            style = Typography.h4
        )

        content.invoke(this)

        WeightedSpacer()

        WideButton(
            text = stringResource(R.string.continue_t),
            modifier = Modifier.padding(bottom = 100.dp)
        ) { continueAction.invoke() }
    }
}