package com.narvatov.datingapp.ui.screen.messages.chat

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.model.local.user.User
import com.narvatov.datingapp.ui.WeightedSpacer
import com.narvatov.datingapp.ui.common.square.SquareReportButton
import com.narvatov.datingapp.ui.navigation.FriendProfile
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.navigate
import com.narvatov.datingapp.ui.noRippleClickable
import com.narvatov.datingapp.ui.theme.GreenActive
import com.narvatov.datingapp.ui.theme.Typography

@Composable
fun ChatFriendBar(friend: User) {
    val activity = LocalContext.current as ComponentActivity

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.back),
            contentDescription = stringResource(R.string.back_button),
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.CenterVertically)
                .clip(CircleShape)
                .clickable {
                    activity.onBackPressedDispatcher.onBackPressed()
                }
        )

        Row(modifier = Modifier
            .padding(start = 20.dp)
            .noRippleClickable {
                navigate(FriendProfile, friend)
            }
        ) {
            Image(
                bitmap = friend.photoBitmap,
                contentScale = ContentScale.Crop,
                contentDescription = stringResource(R.string.profile_image),
                modifier = Modifier.size(48.dp).clip(CircleShape).align(Alignment.CenterVertically),
            )

            Column(modifier = Modifier.padding(start = 12.dp)) {
                Text(
                    text = friend.name,
                    style = Typography.h5,
                )

                Row {
                    if (friend.online) {
                        Spacer(
                            modifier = Modifier
                                .padding(end = 6.dp)
                                .size(8.dp)
                                .background(color = GreenActive, shape = CircleShape)
                                .align(Alignment.CenterVertically)
                        )
                    }

                    Text(
                        text = if (friend.online) stringResource(R.string.online)
                            else stringResource(R.string.last_seen_recently),
                        style = Typography.body1,
                    )
                }
            }
        }

        WeightedSpacer()

        SquareReportButton(friend, modifier = Modifier.align(Alignment.CenterVertically))
    }
}