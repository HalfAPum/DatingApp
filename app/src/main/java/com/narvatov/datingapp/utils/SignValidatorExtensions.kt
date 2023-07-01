package com.narvatov.datingapp.utils

import android.util.Patterns

fun String.isInValidEmail() = this.isBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches().not()

fun String.isInValidPassword() = this.isBlank() && length < 8

fun String.isInValidName() = this.isBlank()