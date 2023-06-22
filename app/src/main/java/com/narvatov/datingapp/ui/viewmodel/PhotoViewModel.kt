package com.narvatov.datingapp.ui.viewmodel

import android.app.Activity
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.delegate.common.context.ContextDelegate
import com.narvatov.datingapp.delegate.common.context.IContextDelegate
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.hidePhotoBottomSheet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class PhotoViewModel : ViewModel(), IContextDelegate by ContextDelegate {

    private val _photoBitmapStateFlow = MutableStateFlow<Bitmap?>(null)
    val photoBitmapStateFlow = _photoBitmapStateFlow.asStateFlow()

    fun onPhotoTook(activityResult: ActivityResult) = with(activityResult) {
        if (resultCode == Activity.RESULT_CANCELED || data == null) return@with

        val bitmap = data?.extras?.get("data") as? Bitmap ?: return@with

        finishPhotoPick(bitmap)
    }

    fun onPhotoSelected(activityResult: ActivityResult) = with(activityResult) {
        if (resultCode == Activity.RESULT_CANCELED || data == null) return@with

        val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, data?.data)

        finishPhotoPick(bitmap)
    }

    //todo remove photo from meomory after sign up is finished
    private fun finishPhotoPick(bitmap: Bitmap) = launchCatching {
        _photoBitmapStateFlow.emit(bitmap)

        hidePhotoBottomSheet()
    }

}