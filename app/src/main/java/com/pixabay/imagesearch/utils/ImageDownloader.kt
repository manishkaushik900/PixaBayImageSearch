package com.pixabay.imagesearch.utils

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment

class ImageDownloader(private val context: Context) : Downloader {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadFile(url: String): Long {
        val request = DownloadManager.Request(Uri.parse(url))
            .setMimeType("image/jpeg")
            .setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI and
                        DownloadManager.Request.NETWORK_MOBILE
            )
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("Image Downloading")
            .setDescription("Please Wait...")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "image.jpeg")

        return downloadManager.enqueue(request)

    }

}