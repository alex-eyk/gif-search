package com.alex.eyk.gifsearch.data.entity

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
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
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        ParcelableUtils.readParcelable(parcel, User::class.java),
        ParcelableUtils.readParcelable(parcel, Images::class.java)
    )

    override fun writeToParcel(
        parcel: Parcel,
        flags: Int
    ) {
        parcel.writeString(id)
        parcel.writeString(url)
        parcel.writeString(shortUrl)
        parcel.writeString(author)
        parcel.writeString(source)
        parcel.writeString(sourceDomain)
        parcel.writeString(rating)
        parcel.writeParcelable(user, flags)
        parcel.writeParcelable(images, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Creator<Gif> {
        override fun createFromParcel(parcel: Parcel): Gif {
            return Gif(parcel)
        }

        override fun newArray(size: Int): Array<Gif?> {
            return arrayOfNulls(size)
        }
    }
}
