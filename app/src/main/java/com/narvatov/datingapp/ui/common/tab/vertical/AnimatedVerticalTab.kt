package com.narvatov.datingapp.ui.common.tab.vertical

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
fun AnimatedVerticalTab(
    items: List<String>,
    modifier: Modifier,
    selectedItemIndex: Int = 0,
    onSelectedTab: (index: Int) -> Unit
) {
    var tabHeight by remember { mutableStateOf(0.dp) }

    val indicatorOffset: Dp by animateDpAsState(
        if (selectedItemIndex == 0) {
            0.dp
        } else {
            (listSpacing * selectedItemIndex) + com.narvatov.datingapp.ui.common.tab.vertical.tabHeight * selectedItemIndex
        }
    )

    with(LocalDensity.current) {
        Box(modifier = modifier.onSizeChanged { size ->
            tabHeight = size.height.toDp()
        }) {
            TabLayoutPositioner(items) { index, _ ->
                GenderTabBackground(
                    modifier = Modifier
                        .height(com.narvatov.datingapp.ui.common.tab.vertical.tabHeight)
                        .fillMaxWidth(),
                    onClick = { onSelectedTab(index) },
                )
            }

            GenderTabIndicator(
                modifier = Modifier
                    .height(com.narvatov.datingapp.ui.common.tab.vertical.tabHeight)
                    .fillMaxWidth(),
                indicatorOffsetY = indicatorOffset
            )

            TabLayoutPositioner(items) { index, title ->
                GenderVerticalTabContent(
                    modifier = Modifier
                        .height(com.narvatov.datingapp.ui.common.tab.vertical.tabHeight)
                        .fillMaxWidth(),
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
    tabContent: @Composable ColumnScope.(Int, String) -> Unit,
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        items.forEachIndexed { index, title ->
            tabContent(index, title)

            if (index != items.lastIndex) {
                Spacer(modifier = Modifier.height(listSpacing).width(spacerStubWidth))
            }
        }
    }
}

private val listSpacing = 12.dp
//Composable with 0.dp doesn't draw
private val spacerStubWidth = 1.dp

private val tabHeight = 58.dp
