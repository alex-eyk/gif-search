package com.alex.eyk.gifsearch.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Gif(

    @SerializedName("id")
    val id: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("bitly_url")
    val shortUrl: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("username")
    val author: String,

    @SerializedName("source")
    val source: String,

    @SerializedName("images")
    val images: Images
) : Parcelable
