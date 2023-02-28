package com.alex.eyk.gifsearch.data.repo.impl

import com.alex.eyk.gifsearch.Either
import com.alex.eyk.gifsearch.data.entity.Gif
import com.alex.eyk.gifsearch.data.net.service.GifService
import com.alex.eyk.gifsearch.data.repo.GifRepository
import com.alex.eyk.gifsearch.data.repo.exception.EmptyResponseBodyException
import com.alex.eyk.gifsearch.data.repo.exception.ServerException
import retrofit2.Response

class RemoteGifRepository(
    private val gifService: GifService
) : GifRepository {

    companion object {

        private const val INTERNAL_SERVER_ERROR = 500
    }

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

    private fun <R : Any, T : Any> handleResponse(
        response: Response<T>,
        toResult: (response: T) -> R
    ): Either<R> {
        return when {
            response.isSuccessful -> {
                val body = response.body()
                when {
                    body != null -> Either.Success(
                        toResult(body)
                    )
                    else -> Either.Failure(EmptyResponseBodyException())
                }
            }
            else -> handleError(response.code())
        }
    }

    private fun handleError(
        code: Int
    ): Either.Failure = when (code) {
        INTERNAL_SERVER_ERROR -> Either.Failure(ServerException())
        else -> Either.Failure(IllegalStateException())
    }
}
