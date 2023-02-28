package com.alex.eyk.gifsearch.data.repo

import com.alex.eyk.gifsearch.Either
import com.alex.eyk.gifsearch.data.entity.Gif

interface GifRepository {

    suspend fun findAll(
        query: String,
        offset: Int
    ): Either<List<Gif>>
}
