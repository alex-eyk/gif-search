package com.alex.eyk.gifsearch.data.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.util.Date

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
    val images: Images,

    @SerializedName("import_datetime")
    val created: Date,

    @SerializedName("trending_datetime")
    val trending: Date
) : Parcelable {

    fun getOptimalUrl(): String {
        return shortUrl.ifEmpty { url }
    }
}
