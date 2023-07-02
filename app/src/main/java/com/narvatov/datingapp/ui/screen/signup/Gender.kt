package com.narvatov.datingapp.ui.screen.signup

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.ui.common.tab.vertical.AnimatedVerticalTab
import com.narvatov.datingapp.ui.screen.filter.child.gender.INVALID_INDEX
import com.narvatov.datingapp.ui.viewmodel.signup.SignUpViewModel

@Composable
fun Gender(viewModel: SignUpViewModel) {
    val stringGenders = genders.map { stringResource(it) }

    var selectedGender by remember { mutableStateOf(stringGenders.first()) }

    val selectedGenderIndex = stringGenders.indexOf(selectedGender).run {
        return@run if (this == INVALID_INDEX) 0
        else this
    }

    val genderChooseAction = {
        viewModel.genderSelected(selectedGender)
    }

    SignUpFlowColumn(
        topTextRes = R.string.i_am_a,
        continueAction = genderChooseAction,
    ) {
        AnimatedVerticalTab(
            modifier = Modifier.fillMaxWidth().padding(top = 50.dp).fillMaxWidth(),
            selectedItemIndex = selectedGenderIndex,
            onSelectedTab = { selectedGender = stringGenders[it] },
            items = stringGenders
        )
    }
}

val genders = listOf(R.string.man, R.string.woman)
