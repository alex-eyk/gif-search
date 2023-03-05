package com.alex.eyk.gifsearch.data.net.pages

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alex.eyk.gifsearch.Either
import com.alex.eyk.gifsearch.data.entity.Gif
import com.alex.eyk.gifsearch.data.net.service.GifService
import com.alex.eyk.gifsearch.data.repo.impl.handleResponse
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

class GifsPagingSource(
    private val gifService: GifService,
    private val query: String
) : PagingSource<Int, Gif>() {

    override fun getRefreshKey(
        state: PagingState<Int, Gif>
    ): Int? {
        val anchorPosition = state.anchorPosition
            ?: return null
        val page = state.closestPageToPosition(anchorPosition)
            ?: return null

        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Gif> {
        if (query.isEmpty()) {
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }
        val page = params.key ?: 1
        val limit = params.loadSize
        val offset = (page - 1) * limit
        val response = gifService.search(query, limit, offset)
        return when (
            val result = handleResponse(response) { it.gifs }
        ) {
            is Either.Success -> {
                val total = response.body()!!.pagination.total
                val nextKey = if (offset + limit < total) page + 1 else null
                val prevKey = if (page != 1) page - 1 else null
                LoadResult.Page(result.value, prevKey, nextKey)
            }
            is Either.Failure -> {
                LoadResult.Error(result.e)
            }
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted("query") query: String): GifsPagingSource
    }
}
