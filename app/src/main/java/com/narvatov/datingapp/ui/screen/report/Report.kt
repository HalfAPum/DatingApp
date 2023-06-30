package com.narvatov.datingapp.ui.screen.report

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.ui.common.button.WideButtonSecondary
import com.narvatov.datingapp.ui.common.header.TextHeader
import com.narvatov.datingapp.ui.theme.Typography
import com.narvatov.datingapp.ui.viewmodel.ReportViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun Report(
    friendId: String,
    viewModel: ReportViewModel = getViewModel(),
) {
    Column(modifier = Modifier.padding(horizontal = 30.dp).padding(top = 20.dp)) {
        TextHeader(R.string.report_block_user)

        Text(
            text = stringResource(R.string.this_report_is_totally_anonymous),
            style = Typography.body1,
            modifier = Modifier.padding(top = 20.dp),
        )

        reportOptions.forEach { reportRes ->
            val reportText = stringResource(reportRes)

            WideButtonSecondary(
                text = reportText,
                modifier = Modifier.padding(top = 12.dp),
            ) { viewModel.reportFriend(friendId, reportText) }
        }
    }
}

private val reportOptions = listOf(
    R.string.fake_photo,
    R.string.inappropriate_profile,
    R.string.abuse,
    R.string.spam_scam,
)