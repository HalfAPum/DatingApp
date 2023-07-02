package com.narvatov.datingapp.ui.screen.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.ui.WeightedSpacer
import com.narvatov.datingapp.ui.common.button.WideButton
import com.narvatov.datingapp.ui.common.button.WideButtonSecondary
import com.narvatov.datingapp.ui.navigation.SignIn
import com.narvatov.datingapp.ui.navigation.SignUpFlow
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.navigate
import com.narvatov.datingapp.ui.theme.TextPrimaryColor
import com.narvatov.datingapp.ui.theme.Typography

@Composable
fun StartPage() {
    Column(modifier = Modifier.padding(horizontal = 30.dp).padding(top = 100.dp)) {
        Image(
            painter = painterResource(R.drawable.start_page_logo),
            contentDescription = stringResource(R.string.friendzilla_find_a_friend_logo),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )

        WideButtonSecondary(
            text = stringResource(R.string.sign_up_to_continue),
            modifier = Modifier.padding(top = 80.dp)
        ) { navigate(SignUpFlow.Credentials) }

        WideButton(
            text = stringResource(R.string.sign_in_with_email),
            modifier = Modifier.padding(top = 20.dp)
        ) { navigate(SignIn) }

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val dividerColor = Color.Black.copy(alpha = 0.4F)

            WeightedSpacer(modifier = Modifier.height(1.dp).background(color = dividerColor))

            Text(
                text = stringResource(R.string.or_sign_in_with),
                modifier = Modifier.padding(horizontal = 12.dp),
                style = Typography.caption,
                color = TextPrimaryColor,
            )

            WeightedSpacer(modifier = Modifier.height(1.dp).background(color = dividerColor))
        }
    }
}