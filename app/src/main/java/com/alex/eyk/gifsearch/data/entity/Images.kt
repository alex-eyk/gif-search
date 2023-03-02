package com.alex.eyk.gifsearch.data.entity

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.google.gson.annotations.SerializedName

data class Images(

    @SerializedName("original")
    val original: OriginalImage
) : Parcelable {

    constructor(parcel: Parcel) : this(
        ParcelableUtils.readParcelable(parcel, OriginalImage::class.java)
            ?: throw IllegalStateException()
    )

    override fun writeToParcel(
        parcel: Parcel,
        flags: Int
    ) {
        parcel.writeParcelable(original, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Creator<Images> {

        override fun createFromParcel(parcel: Parcel): Images {
            return Images(parcel)
        }

        override fun newArray(size: Int): Array<Images?> {
            return arrayOfNulls(size)
        }
    }
}

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
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(
        parcel: Parcel,
        flags: Int
    ) {
        parcel.writeInt(height)
        parcel.writeInt(width)
        parcel.writeInt(size)
        parcel.writeString(url)
        parcel.writeInt(mp4Size)
        parcel.writeString(mp4Url)
        parcel.writeInt(frames)
        parcel.writeString(hash)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Creator<OriginalImage> {

        override fun createFromParcel(parcel: Parcel): OriginalImage {
            return OriginalImage(parcel)
        }

        override fun newArray(size: Int): Array<OriginalImage?> {
            return arrayOfNulls(size)
        }
    }
}
