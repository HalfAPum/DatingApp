package com.narvatov.datingapp.model.local.user

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import com.narvatov.datingapp.R
import com.narvatov.datingapp.utils.toBitmap

data class User(
    val id: String,
    val email: String,
    val password: String,
    val name: String,
    val photoBase64: String,
    val online: Boolean,
    var fcmToken: String,
) {

    val offline = online.not()

    val photoBitmap: ImageBitmap by lazy { photoBase64.toBitmap.asImageBitmap() }

    @Composable
    @ReadOnlyComposable
    fun photoDescription(): String {
        return "$name ${stringResource(R.string.photo)}"
    }

    val isEmpty: Boolean
        get() = this == emptyUser

    companion object {
        val emptyUser = User("","", "", "", "", false, "")
    }

}
