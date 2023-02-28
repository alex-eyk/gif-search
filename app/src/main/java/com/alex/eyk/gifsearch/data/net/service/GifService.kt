package com.alex.eyk.gifsearch.data.net.service

import com.alex.eyk.gifsearch.data.net.dto.GifSearchResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GifService {

    @GET("search")
    suspend fun search(
        @Query("q") query: String,
        @Query("offset") offset: Int
    ): Response<GifSearchResponseBody>
}
