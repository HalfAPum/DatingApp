package com.narvatov.datingapp.ui.common.labeled.texfield

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.ui.theme.BorderColor
import com.narvatov.datingapp.ui.theme.HintGrey
import com.narvatov.datingapp.ui.theme.Typography

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OutlinedLabeledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes
    labelRes: Int,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(labelRes),
            style = Typography.body1,
            fontWeight = FontWeight.Bold,
        )


        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = stringResource(labelRes),
                    color = HintGrey,
                )
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(top = 6.dp),
            colors = ExposedDropdownMenuDefaults.textFieldColors(unfocusedIndicatorColor = BorderColor, backgroundColor = Color.White),
        )
    }
}