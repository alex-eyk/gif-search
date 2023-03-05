package com.alex.eyk.gifsearch

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class GifGlideModule : AppGlideModule() {

    companion object {

        private const val MEMORY_CACHE_SIZE = 24L * 1024L * 1024L
    }

    override fun applyOptions(
        context: Context,
        builder: GlideBuilder
    ) {
        super.applyOptions(context, builder)
        builder
            .setMemoryCache(
                LruResourceCache(MEMORY_CACHE_SIZE)
            )
            .setDiskCache(
                ExternalPreferredCacheDiskCacheFactory(context)
            )
    }
}
