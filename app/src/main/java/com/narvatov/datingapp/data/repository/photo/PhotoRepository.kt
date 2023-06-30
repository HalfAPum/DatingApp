package com.narvatov.datingapp.data.repository.photo

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import com.narvatov.datingapp.data.repository.Repository
import com.narvatov.datingapp.utils.PhotoFileUri
import org.koin.core.annotation.Factory
import timber.log.Timber
import java.io.File

@Factory
class PhotoRepository : Repository() {

    fun getSelectedPhotoBitmap(data: Intent?): Bitmap {
        return MediaStore.Images.Media.getBitmap(context.contentResolver, data?.data)
    }

    fun getTookPhotoBitmap(): Bitmap? {
        val photoFilePath = try {
            val columns = arrayOf(MediaStore.Images.ImageColumns.DATA)
            val cursor = context.contentResolver.query(
                Uri.parse(PhotoFileUri.photoTookUri.toString()),
                columns,
                null,
                null,
                null
            )
            cursor?.moveToFirst()
            val photoPath = cursor?.getString(0)
            cursor?.close()

            photoPath
        } catch (e: Exception) {
            Timber.e(e)

            val columns = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DATE_ADDED)
            val cursor = context.contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                columns,
                null,
                null,
                "\${MediaStore.MediaColumns.DATE_ADDED} DESC"
            )
            val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor?.moveToFirst()
            val photoPath = columnIndex?.let { cursor.getString(it) }
            cursor?.close()

            photoPath
        } ?: return null

        return processTookPhoto(photoFilePath)
    }

    private fun processTookPhoto(photoPath: String): Bitmap? {
        val file = File(photoPath)
        val uriImage = Uri.fromFile(file)
        val bitmap = try {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uriImage)
        } catch (e: java.io.IOException) {
            e.printStackTrace()
            null
        }

        file.delete()

        return bitmap
    }

}