package com.alex.eyk.gifsearch.data.repo

import com.alex.eyk.gifsearch.Either
import com.alex.eyk.gifsearch.data.entity.Suggestion

interface SuggestionsRepository {

    suspend fun findAll(
        query: String
    ): Either<List<Suggestion>>
}
