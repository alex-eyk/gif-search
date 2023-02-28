package com.alex.eyk.gifsearch.data.repo.impl

import com.alex.eyk.gifsearch.Either
import com.alex.eyk.gifsearch.data.entity.Gif
import com.alex.eyk.gifsearch.data.net.service.GifService
import com.alex.eyk.gifsearch.data.repo.GifRepository

class GifRemoteRepository(
    private val gifService: GifService
) : GifRepository {

    override suspend fun findAll(
        query: String,
        offset: Int
    ): Either<List<Gif>> {
        return handleResponse(
            gifService.search(query, offset)
        ) { response ->
            response.gifs
        }
    }
}
