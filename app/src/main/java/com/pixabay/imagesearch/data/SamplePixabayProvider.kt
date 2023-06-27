package com.pixabay.imagesearch.data

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.pixabay.imagesearch.data.entities.ImageItem
import com.pixabay.imagesearch.data.entities.PixabayResponse
import com.pixabay.imagesearch.data.entities.toImageModel
import com.pixabay.imagesearch.domain.entities.MappedImageItemModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object SamplePixabayProvider : PreviewParameterProvider<ImageItem> {
    override val values: Sequence<ImageItem> = sequenceOf(
        ImageItem(
            webformatHeight = 426,
            imageWidth = 4752,
            previewHeight = 99,
            webformatURL = "https://pixabay.com/get/g62eef811a34bd9c15c9269ec772b3729e13b4c75a1578286df917a2397618f7c81e92e56163c97ff272a7e863e09f406_640.jpg",
            userImageURL = "https://cdn.pixabay.com/user/2012/03/08/00-13-48-597_250x250.jpg",
            previewURL = "https://cdn.pixabay.com/photo/2010/12/13/10/05/berries-2277_150.jpg",
            comments = 389,
            imageHeight = 3168,
            tags = "berries,fruits, food",
            previewWidth = 150,
            downloads = 502431,
            collections = 1780,
            largeImageURL = "https://pixabay.com/get/gd633530a25ff1a68c565f37e278c215b78650926392edf43841971bfc710c6dd449b49794fd4d786d7f7e926a6006695_1280.jpg",
            views = 944821,
            likes = 1889,
            userId = 1
        ),
        ImageItem(
            webformatHeight = 426,
            imageWidth = 4752,
            previewHeight = 99,
            webformatURL = "https://pixabay.com/get/g62eef811a34bd9c15c9269ec772b3729e13b4c75a1578286df917a2397618f7c81e92e56163c97ff272a7e863e09f406_640.jpg",
            userImageURL = "https://cdn.pixabay.com/user/2012/03/08/00-13-48-597_250x250.jpg",
            previewURL = "https://cdn.pixabay.com/photo/2010/12/13/10/05/berries-2277_150.jpg",
            comments = 389,
            imageHeight = 3168,
            tags = "berries,fruits, food",
            previewWidth = 150,
            downloads = 502431,
            collections = 1780,
            largeImageURL = "https://pixabay.com/get/gd633530a25ff1a68c565f37e278c215b78650926392edf43841971bfc710c6dd449b49794fd4d786d7f7e926a6006695_1280.jpg",
            views = 944821,
            likes = 1889,
            userId = 2
        )
    )

    fun returnPixabayMappedResponse(): Flow<List<MappedImageItemModel>> {
        return flowOf(values.toList().map { imageItem -> imageItem.toImageModel() })
    }

    fun returnPixabayResponse(): List<ImageItem> {
        return values.toList()
    }
    fun returnPixabayMalformedResponse(): Flow<List<MappedImageItemModel>> {
         throw IllegalStateException("Empty product list")
    }

    val jsonResponse= """
    {
    "total": 34713,
    "totalHits": 500,
    "hits": [
    {
    "id": 2295434,
    "pageURL": "https://pixabay.com/photos/spring-bird-bird-tit-spring-blue-2295434/",
    "type": "photo",
    "tags": "spring bird, bird, tit",
    "previewURL": "https://cdn.pixabay.com/photo/2017/05/08/13/15/spring-bird-2295434_150.jpg",
    "previewWidth": 150,
    "previewHeight": 99,
    "webformatURL": "https://pixabay.com/get/gb1823398f65749778a1bcc9806f41d35d8c339eb40879abc24c7b3c2f963a1c4ba4218e82ca14b43da5a853c7b7ac38ab4ffd16d19be5c3a0ab1e40acebacfb2_640.jpg",
    "webformatWidth": 640,
    "webformatHeight": 426,
    "largeImageURL": "https://pixabay.com/get/gdb1c163005c6489c1d4f326960368d7488452937e26fe145e4ee6e6bd6035cb5a61a53de5958b57ad1febcaecb47f3b1395e0add05ca63d398ac9f95c928d26b_1280.jpg",
    "imageWidth": 5363,
    "imageHeight": 3575,
    "imageSize": 2938651,
    "views": 767980,
    "downloads": 453466,
    "collections": 2232,
    "likes": 2140,
    "comments": 277,
    "user_id": 334088,
    "user": "JillWellington",
    "userImageURL": "https://cdn.pixabay.com/user/2018/06/27/01-23-02-27_250x250.jpg"
    },
    {
    "id": 3063284,
    "pageURL": "https://pixabay.com/photos/rose-flower-petal-floral-noble-3063284/",
    "type": "photo",
    "tags": "rose, flower, petal",
    "previewURL": "https://cdn.pixabay.com/photo/2018/01/05/16/24/rose-3063284_150.jpg",
    "previewWidth": 150,
    "previewHeight": 99,
    "webformatURL": "https://pixabay.com/get/gb205369d18941b6460512237a555e37bfa37dbb8570b33ee4c9bedd54f0185640eab0d917bd356bee78f35fae9495a4e07ebab6e5e17157d0abd9fd995492db0_640.jpg",
    "webformatWidth": 640,
    "webformatHeight": 426,
    "largeImageURL": "https://pixabay.com/get/g3d62f2329bb0e6c5602790f861043bbfea8c84f5744601bfd8b04bac378741262ac5d20c45364a96508bb406ac46411f81d15052f30e79d61c2ef4867af74ba3_1280.jpg",
    "imageWidth": 6000,
    "imageHeight": 4000,
    "imageSize": 3574625,
    "views": 1108258,
    "downloads": 719720,
    "collections": 1472,
    "likes": 1591,
    "comments": 337,
    "user_id": 1564471,
    "user": "anncapictures",
    "userImageURL": "https://cdn.pixabay.com/user/2015/11/27/06-58-54-609_250x250.jpg"
    }
    ]
    }
""".trimIndent()

    val emptyListResponse = """
        {
    "total": 34713,
    "totalHits": 500,
    "hits": []
    }
    """.trimIndent()

    val failureResponse = """ {} """.trimIndent()

    fun convertJsonToModel(response:String): PixabayResponse {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<PixabayResponse> = moshi.adapter(PixabayResponse::class.java)
        return adapter.fromJson(response)!!
    }
}