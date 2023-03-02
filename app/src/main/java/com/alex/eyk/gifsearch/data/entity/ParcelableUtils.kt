package com.alex.eyk.gifsearch.data.entity

import android.os.Build.VERSION
import android.os.Parcel
import android.os.Parcelable

object ParcelableUtils {

    internal fun <T : Parcelable> readParcelable(
        parcel: Parcel,
        clazz: Class<T>
    ): T? {
        val parcelable: T?
        if (VERSION.SDK_INT < 33) {
            @Suppress("DEPRECATION")
            parcelable = parcel.readParcelable(clazz.classLoader)
        } else {
            parcelable = parcel.readParcelable(clazz.classLoader, clazz)
        }
        return parcelable
    }
}
