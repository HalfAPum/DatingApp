package com.narvatov.datingapp.ui.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.halfapum.general.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class PhotoViewModel : ViewModel() {

    private val _photoBitmapStateFlow = MutableStateFlow<Bitmap?>(null)
    val photoBitmapStateFlow = _photoBitmapStateFlow.asStateFlow()

    fun onPhotoChosen(bitmap: Bitmap) = launch {
        _photoBitmapStateFlow.emit(bitmap)
    }

}