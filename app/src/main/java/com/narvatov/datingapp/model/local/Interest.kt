package com.narvatov.datingapp.model.local

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Interest(
    @DrawableRes
    val icon: Int,
    @StringRes
    val text: Int,
    val selected: Boolean = false,
)