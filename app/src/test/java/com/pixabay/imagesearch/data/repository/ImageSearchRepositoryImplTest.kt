package com.pixabay.imagesearch.data.repository

import com.google.common.truth.Truth
import com.pixabay.imagesearch.data.SamplePixabayProvider
import com.pixabay.imagesearch.data.SamplePixabayProvider.convertJsonToModel
import com.pixabay.imagesearch.data.entities.toImageModel
import com.pixabay.imagesearch.data.remote.ApiService
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class ImageSearchRepositoryImplTest {

    private val mockWebServer = MockWebServer()

    private lateinit var repository: ImageSearchRepositoryImpl
    private lateinit var dataSource: NetworkDataSource

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private var apiService: ApiService = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .build()
        .create(ApiService::class.java)

    @Before
    fun setUp() {
        dataSource = NetworkDataSource(apiService)
        repository = ImageSearchRepositoryImpl(dataSource)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `successfully fetches album list return success response`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(SamplePixabayProvider.jsonResponse)
        )

        val response = repository.fetchSearchData("apple")
        Truth.assertThat(response).isNotNull()

        val expected = convertJsonToModel(SamplePixabayProvider.jsonResponse).hits.map { it.toImageModel() }
        Truth.assertThat(response).isEqualTo(expected)
    }

    //    @Test
    @Test(expected = IllegalStateException::class)
    fun `successfully fetches album list return empty list success response`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(SamplePixabayProvider.emptyListResponse)
        )

        val response = repository.fetchSearchData("apple")
        Truth.assertThat(response).isEqualTo(IllegalStateException("Empty product list"))
    }

    @Test(expected = retrofit2.HttpException::class)
    fun `error occured while fetching return error response`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setBody(SamplePixabayProvider.failureResponse)
        )

        val response = repository.fetchSearchData("apple")
        Truth.assertThat(response).isInstanceOf(retrofit2.HttpException::class.java)
    }
}

