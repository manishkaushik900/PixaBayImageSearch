package com.pixabay.imagesearch.ui.commons

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment

interface Downloader {

    fun downloadFile(url: String): Long

}
internal class ImageDownloader(private val context: Context) : Downloader {

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

class DownloadCompletedReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if(intent?.action == "android.intent.action.DOWNLOAD_COMPLETE"){

            val id =  intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1L)

            if( id != -1L){
                println("Download with ID $id failed")
            }
        }
    }
}

