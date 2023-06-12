package com.pixabay.imagesearch.utils

interface Downloader {

    fun downloadFile(url: String): Long

}