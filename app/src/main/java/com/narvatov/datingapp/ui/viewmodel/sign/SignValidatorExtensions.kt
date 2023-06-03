package com.narvatov.datingapp.ui.viewmodel.sign

import android.util.Patterns

internal fun String.isInValidEmail() = this.isBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches().not()

internal fun String.isInValidPassword() = this.isBlank() && length < 8

internal fun String.isInValidName() = this.isBlank()