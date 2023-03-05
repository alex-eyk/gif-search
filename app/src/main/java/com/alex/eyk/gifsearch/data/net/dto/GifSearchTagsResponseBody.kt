package com.alex.eyk.gifsearch.data.net.dto

import com.alex.eyk.gifsearch.data.entity.Suggestion
import com.google.gson.annotations.SerializedName

data class GifSearchTagsResponseBody(

    @SerializedName("data")
    val suggestion: List<Suggestion>
)
