package com.alex.eyk.gifsearch.data.net.dto

import com.google.gson.annotations.SerializedName

data class Pagination(

    @SerializedName("total_count")
    val total: Int
)
