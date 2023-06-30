package com.narvatov.datingapp.ui.screen.filter.child

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForwardIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.ui.WeightedSpacer
import com.narvatov.datingapp.ui.theme.BorderColor
import com.narvatov.datingapp.ui.theme.PrimaryColor
import com.narvatov.datingapp.ui.theme.Shapes
import com.narvatov.datingapp.ui.theme.TextPrimaryColor
import com.narvatov.datingapp.ui.theme.Typography
import com.narvatov.datingapp.utils.UnitCallback

@Composable
fun ClickableFilterField(
    @StringRes
    titleRes: Int,
    modifier: Modifier,
    onClick: UnitCallback,
    content: @Composable RowScope.() -> Unit,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(titleRes),
            style = Typography.body1,
            color = TextPrimaryColor,
            fontWeight = FontWeight.Bold,
        )

        Row(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .height(58.dp)
                .background(color = Color.White, shape = Shapes.small)
                .clip(shape = Shapes.small)
                .clickable { onClick.invoke() }
                .border(width = 1.dp, color = BorderColor, shape = Shapes.small)
        ) {
            Row(modifier = Modifier.padding(horizontal = 16.dp).fillMaxSize()) {
                content.invoke(this)

                WeightedSpacer()

                Image(
                    imageVector = Icons.Rounded.ArrowForwardIos,
                    contentDescription = stringResource(R.string.arrow_next),
                    modifier = Modifier.size(16.dp).align(Alignment.CenterVertically),
                    colorFilter = ColorFilter.tint(PrimaryColor)
                )
            }
        }
    }
}