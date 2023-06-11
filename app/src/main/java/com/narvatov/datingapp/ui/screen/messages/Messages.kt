package com.narvatov.datingapp.ui.screen.messages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.ui.theme.Typography
import com.narvatov.datingapp.ui.viewmodel.messages.MessagesViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun Messages(
    viewModel: MessagesViewModel = getViewModel()
) {
    val users by viewModel.usersStateFlow.collectAsState()

    LazyColumn {
        items(users) { user ->
            Row(Modifier.padding(vertical = 10.dp, horizontal = 20.dp)) {
                Image(
                    bitmap = user.photoBitmap.asImageBitmap(),
                    contentScale = ContentScale.Crop,
                    contentDescription = user.name + stringResource(R.string.space_photo),
                    modifier = Modifier.size(100.dp),
                )

                Text(
                    text = user.name,
                    style = Typography.h6,
                    modifier = Modifier.align(Alignment.CenterVertically).padding(start = 10.dp)
                )
            }
        }
    }
}