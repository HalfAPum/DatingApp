package com.narvatov.datingapp.ui.screen.filter.child.gender

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.ui.theme.TextPrimaryColor
import com.narvatov.datingapp.ui.theme.Typography

@Composable
fun GenderFilter(
    selectedGender: String,
    modifier: Modifier = Modifier,
    onGenderSelected: (String) -> Unit,
) {
    val stringGenders = genders.map { stringResource(it) }

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.gender),
            style = Typography.body1,
            color = TextPrimaryColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 30.dp)
        )

        val selectedGenderIndex = stringGenders.indexOf(selectedGender).run {
            return@run if (this == INVALID_INDEX) 0
            else this
        }

        AnimatedTab(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp).height(58.dp),
            selectedItemIndex = selectedGenderIndex,
            onSelectedTab = { onGenderSelected(stringGenders[it]) },
            items = stringGenders
        )
    }
}

val genders = listOf(R.string.male, R.string.female, R.string.both)

private const val INVALID_INDEX = -1