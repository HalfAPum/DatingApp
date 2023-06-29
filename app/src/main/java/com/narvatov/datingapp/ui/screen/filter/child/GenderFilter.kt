package com.narvatov.datingapp.ui.screen.filter.child

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.R
import com.narvatov.datingapp.ui.noRippleClickable
import com.narvatov.datingapp.ui.theme.BorderColor
import com.narvatov.datingapp.ui.theme.OnPrimaryColor
import com.narvatov.datingapp.ui.theme.PrimaryColor
import com.narvatov.datingapp.ui.theme.Shapes
import com.narvatov.datingapp.ui.theme.TextPrimaryColor
import com.narvatov.datingapp.ui.theme.Typography

@Composable
fun GenderFilter(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.gender),
            style = Typography.body1,
            color = TextPrimaryColor,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 30.dp)
        )

        var selectedTab by remember { mutableStateOf(0) }

        AnimatedTab(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp).height(58.dp),
            selectedItemIndex = selectedTab,
            onSelectedTab = { selectedTab = it },
            items = genders
        )
    }
}

private val genders = listOf(R.string.male, R.string.female, R.string.both)

@Composable
fun AnimatedTab(
    items: List<Int>,
    modifier: Modifier,
    selectedItemIndex: Int = 0,
    onSelectedTab: (index: Int) -> Unit
) {

    var tabWidth by remember { mutableStateOf(0.dp) }

    val spacerWidth = 8.dp

    val indicatorOffset: Dp by animateDpAsState(
        if (selectedItemIndex == 0) {
            30.dp
        } else {
            30.dp + (spacerWidth * selectedItemIndex) + ((tabWidth - (spacerWidth * 2) - 60.dp) / items.size) * selectedItemIndex
        }
    )

    with(LocalDensity.current) {
        Box(modifier = modifier.onSizeChanged { size ->
            tabWidth = size.width.toDp()
        }) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Spacer(modifier = Modifier.height(1.dp).width(30.dp))

                items.forEachIndexed { index, _ ->
                    MyTabItemBackground(
                        modifier = Modifier
                            .height(58.dp)
                            .weight(1F),
                        onClick = {
                            onSelectedTab(index)
                        },
                    )

                    if (index != items.lastIndex) {
                        Spacer(modifier = Modifier.height(1.dp).width(spacerWidth))
                    }
                }

                Spacer(modifier = Modifier.height(1.dp).width(30.dp))
            }


            MyTabIndicator(
                modifier = Modifier
                    .height(58.dp)
                    .width((tabWidth - (spacerWidth * 2) - 60.dp) / items.size),
                indicatorOffset = indicatorOffset
            )

            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Spacer(modifier = Modifier.height(1.dp).width(30.dp))

                items.forEachIndexed { index, title ->
                    MyTabItemText(
                        modifier = Modifier
                            .height(58.dp)
                            .weight(1F),
                        onClick = {
                            onSelectedTab(index)
                        },
                        title = stringResource(title),
                        selected = selectedItemIndex == index,
                    )

                    if (index != items.lastIndex) {
                        Spacer(modifier = Modifier.height(1.dp).width(spacerWidth))
                    }
                }

                Spacer(modifier = Modifier.height(1.dp).width(30.dp))
            }
        }
    }
}

@Composable
private fun MyTabIndicator(
    modifier: Modifier,
    indicatorOffset: Dp,
) {
    Box(
        modifier = modifier
            .offset(x = indicatorOffset)
            .clip(Shapes.small)
            .background(PrimaryColor)
    )
}


@Composable
private fun MyTabItemBackground(
    modifier: Modifier,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .background(color = Color.White, shape = Shapes.small)
            .border(width = 1.dp, color = BorderColor, shape = Shapes.small)
            .noRippleClickable { onClick() },
    )
}


@Composable
private fun MyTabItemText(
    modifier: Modifier,
    onClick: () -> Unit,
    title: String,
    selected: Boolean
) {
    val color by animateColorAsState(if (selected) OnPrimaryColor else TextPrimaryColor)
    Box(
        modifier = modifier.noRippleClickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = Typography.body2,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}