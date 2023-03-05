package com.alex.eyk.gifsearch.ui

import com.alex.eyk.gifsearch.Either

sealed class UiState<out T : Any> {

    object None : UiState<Nothing>()

    object Loading : UiState<Nothing>()

    data class Success<out T : Any>(val value: T) : UiState<T>()

    data class Failure(val e: Exception) : UiState<Nothing>()

    companion object {

        inline fun <T : Any> by(
            block: () -> Either<T>
        ): UiState<T> {
            return when (val result = block()) {
                is Either.Success -> Success(result.value)
                is Either.Failure -> Failure(result.e)
            }
        }
    }
}
