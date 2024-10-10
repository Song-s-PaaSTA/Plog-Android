package com.kpaas.plog.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

fun uriToFile(uri: Uri, context: Context): File {
    val inputStream = context.contentResolver.openInputStream(uri)
        ?: throw IllegalArgumentException("Cannot open InputStream for Uri: $uri")

    val bitmap = BitmapFactory.decodeStream(inputStream)

    val outputStream = ByteArrayOutputStream()
    val quality = 85 // 압축 품질 (0-100 범위)
    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)

    val file = File(context.cacheDir, "compressed_image.jpg")
    FileOutputStream(file).use { fos ->
        outputStream.writeTo(fos)
    }

    return file
}