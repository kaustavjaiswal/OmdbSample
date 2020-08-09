package com.kaustavjaiswal.omdbsampleapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kaustavjaiswal.omdbsampleapp.dataSource.remote.api.OmdbService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@RunWith(JUnit4::class)
class OmdbServiceTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: OmdbService

    private lateinit var mockWebServer: MockWebServer

    private val map = HashMap<String, String>()

    init {
        map["apikey"] = "sampleKey"
    }

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(OmdbService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun `validate response result metadata`() = runBlocking {
        // Given
        enqueueResponse("search_result.json")
        map["s"] = "samplesearch"

        // When
        val resultResponse = service.searchOmdb(1, map)

        // Then
        Assert.assertNotNull(resultResponse)
        Assert.assertEquals(resultResponse.totalResults, "1729")
        Assert.assertEquals(resultResponse.response, "True")
    }

    @Test
    fun `validate single photo response object`() = runBlocking {
        // Given
        enqueueResponse("search_result.json")
        map["s"] = "samplesearch"

        // When
        val resultResponse = service.searchOmdb(1, map)
        val response = resultResponse.showData

        // Then
        val searchResult = response.first()

        Assert.assertEquals(searchResult.title, "Friends")
        Assert.assertEquals(searchResult.year, "1994–2004")
        Assert.assertEquals(searchResult.imdbID, "tt0108778")
        Assert.assertEquals(searchResult.type, "series")
        Assert.assertEquals(
            searchResult.poster,
            "https://m.media-amazon.com/images/M/MV5BNDVkYjU0MzctMWRmZi00NTkxLTgwZWEtOWVhYjZlYjllYmU4XkEyXkFqcGdeQXVyNTA4NzY1MzY@._V1_SX300.jpg"
        )
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!
            .getResourceAsStream("response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setBody(source.readString(Charsets.UTF_8))
        )
    }
}