package com.yesitlab.compro.base

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore




class Path1 {
    companion object {
        @SuppressLint("ObsoleteSdkInt")

        /*
        fun getPath1(context: Context?, uri: Uri): String? {
            if (context == null) return null

            val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

            // DocumentProvider
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                when {
                    isExternalStorageDocument(uri) -> {
                        val docId = DocumentsContract.getDocumentId(uri)
                        val split = docId.split(":")
                        val type = split[0]
                        if ("primary".equals(type, true)) {
                            return "${Environment.getExternalStorageDirectory()}/${split[1]}"
                        }
                    }
                    isDownloadsDocument(uri) -> {
                        val id = DocumentsContract.getDocumentId(uri)
                        if (id.startsWith("raw:")) {
                            return id.removePrefix("raw:")
                        }
                        val contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"),
                            id.toLongOrNull() ?: return null
                        )
                        return getDataColumn(context, contentUri, null, null)
                    }
                    isMediaDocument(uri) -> {
                        val docId = DocumentsContract.getDocumentId(uri)
                        val split = docId.split(":")
                        val type = split[0]
                        val contentUri = when (type) {
                            "image" -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            "video" -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                            "audio" -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                            else -> null
                        }
                        val selection = "_id=?"
                        val selectionArgs = arrayOf(split[1])
                        return getDataColumn(context, contentUri, selection, selectionArgs)
                    }
                }
            } else if ("content".equals(uri.scheme, true)) {
                return getDataColumn(context, uri, null, null)
            } else if ("file".equals(uri.scheme, true)) {
                return uri.path
            }
            return null
        }

        private fun getDataColumn(
            context: Context,
            uri: Uri?,
            selection: String?,
            selectionArgs: Array<String>?
        ): String? {
            var cursor: Cursor? = null
            val column = "_data"
            val projection = arrayOf(column)
            try {
                cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
                if (cursor != null && cursor.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndexOrThrow(column)
                    return cursor.getString(columnIndex)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor?.close()
            }
            return null
        }

         */
         fun getPath1(context: Context, uri: Uri): String? {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, uri)) {
                when {
                    uri.authority == "com.android.externalstorage.documents" -> {
                        val docId = DocumentsContract.getDocumentId(uri)
                        val split = docId.split(":")
                        if (split[0] == "primary") {
                            return "${context.getExternalFilesDir(null)}/${split[1]}"
                        }
                    }
                    uri.authority == "com.android.providers.downloads.documents" -> {
                        val id = DocumentsContract.getDocumentId(uri)
                        val contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"),
                            id.toLongOrNull() ?: return null
                        )
                        return getDataColumn(context, contentUri, null, null)
                    }
                    uri.authority == "com.android.providers.media.documents" -> {
                        val docId = DocumentsContract.getDocumentId(uri)
                        val split = docId.split(":")
                        val contentUri = when (split[0]) {
                            "image" -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            "video" -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                            "audio" -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                            else -> null
                        }
                        return contentUri?.let {
                            getDataColumn(context,
                                it, "_id=?", arrayOf(split[1]))
                        }
                    }
                }
            } else if (uri.scheme == "content") {
                return getDataColumn(context, uri, null, null)
            } else if (uri.scheme == "file") {
                return uri.path
            }
            return null
        }

         fun getDataColumn(context: Context, uri: Uri,
                                  selection: String?,
                                  selectionArgs: Array<String>?): String? {
            val projection = arrayOf("_data")
            context.contentResolver.query(uri, projection, selection, selectionArgs, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    return cursor.getString(cursor.getColumnIndexOrThrow("_data"))
                }
            }
            return null
        }


        private fun isExternalStorageDocument(uri: Uri): Boolean {
            return "com.android.externalstorage.documents" == uri.authority
        }

        private fun isDownloadsDocument(uri: Uri): Boolean {
            return "com.android.providers.downloads.documents" == uri.authority
        }

        private fun isMediaDocument(uri: Uri): Boolean {
            return "com.android.providers.media.documents" == uri.authority
        }
    }
}
