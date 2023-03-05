package com.alex.eyk.gifsearch.data.net.dto

import com.alex.eyk.gifsearch.data.entity.Gif
import com.google.gson.annotations.SerializedName

data class GifSearchResponseBody(

    @SerializedName("data")
    val gifs: List<Gif>,

    @SerializedName("pagination")
    val pagination: Pagination
)
