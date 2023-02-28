package com.alex.eyk.gifsearch.data.net.service

import com.alex.eyk.gifsearch.data.net.dto.GifSearchResponseBody
import com.alex.eyk.gifsearch.data.net.dto.GifSearchTagsResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GifService {

    @GET("search")
    suspend fun search(
        @Query("q") query: String,
        @Query("offset") offset: Int
    ): Response<GifSearchResponseBody>

    @GET("search/tags")
    suspend fun searchTags(
        @Query("q") query: String
    ): Response<GifSearchTagsResponseBody>
}
