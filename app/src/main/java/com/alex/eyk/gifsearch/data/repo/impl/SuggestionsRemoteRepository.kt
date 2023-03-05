package com.alex.eyk.gifsearch.data.repo.impl

import com.alex.eyk.gifsearch.Either
import com.alex.eyk.gifsearch.data.entity.Suggestion
import com.alex.eyk.gifsearch.data.net.handleResponse
import com.alex.eyk.gifsearch.data.net.service.GifService
import com.alex.eyk.gifsearch.data.repo.SuggestionsRepository

class SuggestionsRemoteRepository(
    private val gifService: GifService
) : SuggestionsRepository {

    override suspend fun findAll(
        query: String
    ): Either<List<Suggestion>> {
        return handleResponse(
            gifService.searchTags(query)
        ) { response ->
            response.suggestion
        }
    }
}
