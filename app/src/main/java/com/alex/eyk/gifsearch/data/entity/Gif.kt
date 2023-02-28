package com.alex.eyk.gifsearch.data.entity

import com.google.gson.annotations.SerializedName

data class Gif(

    @SerializedName("id")
    val id: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("bitly_url")
    val shortUrl: String,

    @SerializedName("username")
    val author: String,

    @SerializedName("source")
    val source: String,

    @SerializedName("source_rld")
    val sourceDomain: String,

    @SerializedName("rating")
    val rating: String,

    @SerializedName("user")
    val user: User?,

    @SerializedName("images")
    val images: Images
)
