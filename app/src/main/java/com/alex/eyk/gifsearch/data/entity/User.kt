package com.alex.eyk.gifsearch.data.entity

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("avatar_url")
    val avatarUrl: String,

    @SerializedName("banner_url")
    val bannerUrl: String,

    @SerializedName("profile_url")
    val profileUrl: String,

    @SerializedName("username")
    val username: String,

    @SerializedName("display_name")
    val displayName: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(avatarUrl)
        parcel.writeString(bannerUrl)
        parcel.writeString(profileUrl)
        parcel.writeString(username)
        parcel.writeString(displayName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}
