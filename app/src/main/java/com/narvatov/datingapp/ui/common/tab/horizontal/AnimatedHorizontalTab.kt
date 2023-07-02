package com.narvatov.datingapp.ui.common.tab.horizontal

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.narvatov.datingapp.ui.common.tab.GenderTabBackground
import com.narvatov.datingapp.ui.common.tab.GenderTabIndicator

@Composable
fun AnimatedHorizontalTab(
    items: List<String>,
    modifier: Modifier,
    selectedItemIndex: Int = 0,
    onSelectedTab: (index: Int) -> Unit
) {
    var tabWidth by remember { mutableStateOf(0.dp) }

    val indicatorWidth = (tabWidth - (listSpacing * 2) - (horizontalPadding * 2)) / items.size

    val indicatorOffset: Dp by animateDpAsState(
        if (selectedItemIndex == 0) {
            horizontalPadding
        } else {
            horizontalPadding + (listSpacing * selectedItemIndex) + indicatorWidth * selectedItemIndex
        }
    )

    with(LocalDensity.current) {
        Box(modifier = modifier.onSizeChanged { size ->
            tabWidth = size.width.toDp()
        }) {
            TabLayoutPositioner(items) { index, _ ->
                GenderTabBackground(
                    modifier = Modifier
                        .height(tabHeight)
                        .weight(1F),
                    onClick = { onSelectedTab(index) },
                )
            }

            GenderTabIndicator(
                modifier = Modifier
                    .height(tabHeight)
                    .width(indicatorWidth),
                indicatorOffsetX = indicatorOffset
            )

            TabLayoutPositioner(items) { index, title ->
                GenderHorizontalTabContent(
                    modifier = Modifier
                        .height(tabHeight)
                        .weight(1F),
                    onClick = { onSelectedTab(index) },
                    title = title,
                    selected = selectedItemIndex == index,
                )
            }
        }
    }
}

@Composable
private fun TabLayoutPositioner(
    items: List<String>,
    tabContent: @Composable RowScope.(Int, String) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Spacer(modifier = Modifier.height(spacerStubHeight).width(horizontalPadding))

        items.forEachIndexed { index, title ->
            tabContent(index, title)

            if (index != items.lastIndex) {
                Spacer(modifier = Modifier.height(spacerStubHeight).width(listSpacing))
            }
        }

        Spacer(modifier = Modifier.height(spacerStubHeight).width(horizontalPadding))
    }
}

private val listSpacing = 8.dp
private val horizontalPadding = 30.dp
//Composable with 0.dp doesn't draw
private val spacerStubHeight = 1.dp

private val tabHeight = 58.dp
