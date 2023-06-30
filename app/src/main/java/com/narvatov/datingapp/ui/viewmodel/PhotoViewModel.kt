package com.narvatov.datingapp.ui.viewmodel

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import com.halfapum.general.coroutines.launchCatching
import com.narvatov.datingapp.delegate.common.context.ContextDelegate
import com.narvatov.datingapp.delegate.common.context.IContextDelegate
import com.narvatov.datingapp.model.local.action.Action
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.actionFlow
import com.narvatov.datingapp.ui.navigation.UiNavigationEventPropagator.hidePhotoBottomSheet
import com.narvatov.datingapp.utils.PhotoFileUri
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import org.koin.android.annotation.KoinViewModel
import java.io.File


@KoinViewModel
class PhotoViewModel : ViewModel(), IContextDelegate by ContextDelegate {

    private val _photoBitmapStateFlow = MutableStateFlow<Pair<Bitmap?, Uri?>>(null to null)
    val photoBitmapStateFlow = _photoBitmapStateFlow.asStateFlow()

    init {
        launchCatching {
            actionFlow.collectLatest { action ->
                if (action is Action.SignAction) {
                    clearStateFlow()
                }
            }
        }
    }

    fun onPhotoTook(activityResult: ActivityResult) = with(activityResult) {
        if (resultCode == Activity.RESULT_CANCELED) return@with

        try {
            val columns = arrayOf(MediaStore.Images.ImageColumns.DATA)
            val cursor = context.contentResolver.query(Uri.parse(PhotoFileUri.photoTookUri.toString()), columns, null, null, null)
            cursor?.moveToFirst()
            val photoPath = cursor?.getString(0) ?: return@with
            cursor.close()
            val file = File(photoPath)
            val uriImage = Uri.fromFile(file)
            val bitmap = try {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uriImage)
            } catch (e: java.io.IOException) {
                e.printStackTrace()
                return@with
            }

            finishPhotoPick(bitmap, uriImage)

            file.delete()
        } catch (e: Exception) {
            val columns = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DATE_ADDED)
            val cursor = context.contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                columns,
                null,
                null,
                "\${MediaStore.MediaColumns.DATE_ADDED} DESC"
            )
            val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA) ?: return@with
            cursor.moveToFirst()
            val photoPath = cursor.getString(columnIndex)
            cursor.close()
            val file = File(photoPath)
            val uriImage = Uri.fromFile(file)
            val bitmap = try {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uriImage)
            } catch (e: java.io.IOException) {
                e.printStackTrace()
                return@with
            }

            finishPhotoPick(bitmap, uriImage)

            file.delete()
        }
    }

    fun onPhotoSelected(activityResult: ActivityResult) = with(activityResult) {
        if (resultCode == Activity.RESULT_CANCELED || data == null) return@with

        val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, data?.data)

        finishPhotoPick(bitmap)
    }

    private fun finishPhotoPick(bitmap: Bitmap) = launchCatching {
        _photoBitmapStateFlow.emit(bitmap to null)

        hidePhotoBottomSheet()
    }


    private fun finishPhotoPick(bitmap: Bitmap, uri: Uri) = launchCatching {
        _photoBitmapStateFlow.emit(bitmap to uri)

        hidePhotoBottomSheet()
    }

    private fun clearStateFlow() = launchCatching {
        //Do some delay to avoid selected image blinking when navigate to next screen
        delay(200L)

        _photoBitmapStateFlow.emit(null to null)
    }

}