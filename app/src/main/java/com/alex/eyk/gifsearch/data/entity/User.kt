package com.alex.eyk.gifsearch.data.entity

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
)
