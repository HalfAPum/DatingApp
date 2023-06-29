package com.narvatov.datingapp.ui.screen.messages.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.ui.theme.BorderColor
import com.narvatov.datingapp.ui.theme.ChatBackground
import com.narvatov.datingapp.ui.theme.HintGrey
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SendMessageBar(messagesListState: LazyListState, sendMessage: (String) -> Unit) {
    val scrollScope = rememberCoroutineScope()

    var message by rememberSaveable { mutableStateOf("") }

    val sendMessageAction = {
        sendMessage.invoke(message)

        message = ""

        scrollScope.launch { messagesListState.animateScrollToItem(0, 0) }
    }

    Box(modifier = Modifier.background(color = ChatBackground)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                placeholder = {
                    Text(
                        text = stringResource(R.string.your_message),
                        color = HintGrey,
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    sendMessageAction.invoke()
                }),
                maxLines = 6,
                modifier = Modifier.weight(1F),
                colors = textFieldColors(unfocusedIndicatorColor = BorderColor),
            )

            Button(
                onClick = { sendMessageAction.invoke() },
                modifier = Modifier
                    .padding(start = 20.dp)
                    .size(TextFieldDefaults.MinHeight),
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(R.drawable.send),
                        contentDescription = stringResource(R.string.send_message),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}