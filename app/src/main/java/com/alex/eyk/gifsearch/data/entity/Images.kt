package com.alex.eyk.gifsearch.data.entity

import com.google.gson.annotations.SerializedName

data class Images(

    @SerializedName("original")
    val original: OriginalImage
)

data class OriginalImage(

    @SerializedName("height")
    val height: Int,

    @SerializedName("width")
    val width: Int,

    @SerializedName("size")
    val size: Int,

    @SerializedName("url")
    val url: String,

    @SerializedName("mp4_size")
    val mp4Size: Int,

    @SerializedName("mp4")
    val mp4Url: String,

    @SerializedName("frames")
    val frames: Int,

    @SerializedName("hash")
    val hash: String
)
