package com.alex.eyk.gifsearch.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Images(

    @SerializedName("original")
    val original: OriginalImage
) : Parcelable

@Parcelize
data class OriginalImage(

    @SerializedName("height")
    val height: Int,

    @SerializedName("width")
    val width: Int,

    @SerializedName("size")
    val size: Int,

    @SerializedName("url")
    val url: String,

    @SerializedName("frames")
    val frames: Int
) : Parcelable

@Parcelize
data class DownsizedImage(

    @SerializedName("height")
    val height: Int,

    @SerializedName("width")
    val width: Int,

    @SerializedName("size")
    val size: Int,

    @SerializedName("url")
    val url: String
) : Parcelable
