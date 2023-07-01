package com.narvatov.datingapp.ui.screen.filter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.ui.common.button.WideButton
import com.narvatov.datingapp.ui.common.button.WideButtonSecondary
import com.narvatov.datingapp.ui.common.header.TextHeader
import com.narvatov.datingapp.ui.screen.filter.child.AGE_RANGE_OPTIMAL_MAX
import com.narvatov.datingapp.ui.screen.filter.child.AGE_RANGE_START
import com.narvatov.datingapp.ui.screen.filter.child.AgeFilter
import com.narvatov.datingapp.ui.screen.filter.child.ClickableFilterField
import com.narvatov.datingapp.ui.screen.filter.child.gender.GenderFilter
import com.narvatov.datingapp.ui.screen.filter.child.gender.genders
import com.narvatov.datingapp.ui.theme.Typography

@Composable
fun ConnectFilter() {
    val scrollState = rememberScrollState()

    Column {
        TextHeader(
            R.string.filter,
            modifier = Modifier.padding(horizontal = 30.dp).padding(top = 20.dp)
        )

        Column(modifier = Modifier.verticalScroll(scrollState)) {
            val initialGender = stringResource(genders.first())

            var gender by remember { mutableStateOf(initialGender) }

            GenderFilter(
                selectedGender = gender,
                modifier = Modifier.padding(top = 20.dp),
            ) { gender = it }

            ClickableFilterField(
                titleRes = R.string.interests,
                modifier = Modifier.padding(top = 16.dp).padding(horizontal = 30.dp),
                onClick = {

                }
            ) {
                Text(
                    text = "Football fans",
                    style = Typography.body2,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

            ClickableFilterField(
                titleRes = R.string.location,
                modifier = Modifier.padding(top = 16.dp).padding(horizontal = 30.dp),
                onClick = {

                }
            ) {
                Text(
                    text = "Toronto, Canada",
                    style = Typography.body2,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

            var ageRange by remember { mutableStateOf(AGE_RANGE_START..AGE_RANGE_OPTIMAL_MAX) }

            AgeFilter(
                ageRange = ageRange,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .padding(horizontal = 20.dp)
            ) { ageRange = it }

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

            Spacer(modifier = Modifier.height(50.dp).width(1.dp))
        }
    }
}