package com.narvatov.datingapp.ui

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun Scaffold(
    navController: NavHostController,
    bottomBar: @Composable (NavHostController) -> Unit = {},
    content: @Composable (NavHostController, PaddingValues) -> Unit
) {
    androidx.compose.material.Scaffold(
        bottomBar = { bottomBar(navController) },
        content = { paddingValues -> content(navController, paddingValues) },
    )
}

@Composable
fun RowScope.WeightedSpacer(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.weight(1F))
}

@Composable
fun ColumnScope.WeightedSpacer(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.weight(1F))
}

fun LazyListScope.ListSpacer(modifier: Modifier = Modifier) {
    item { Spacer(modifier = modifier) }
}